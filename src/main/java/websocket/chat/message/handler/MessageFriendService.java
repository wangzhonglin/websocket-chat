package websocket.chat.message.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.message.vo.WsFriendRequest;
import websocket.chat.message.vo.WsFriendResponse;
import websocket.chat.message.vo.WsSessionResponse;
import websocket.chat.session.service.SessionService;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;
import websocket.chat.util.JsonUtil;
import websocket.chat.websocket.manage.WebsocketChannelManager;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

/**
 * MessageFriendService
 * Date: 2016-04-21
 *
 * @author wangzhonglin
 */
@Service
public class MessageFriendService extends MessageBaseService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageFriendService.class);

    @Autowired
    private WebsocketChannelManager websocketChannelManager;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx) {
        WsFriendRequest friendRequest = JsonUtil.safelyParseObject(requestVO.getRequest(), WsFriendRequest.class);
        if (friendRequest == null) {
            LOG.info(Constant.NULL_PARAM_MESSAGE, requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.NULL_PARAM_MESSAGE));
        }
        if (!checkLogin(requestVO.getLoginSessionId(), friendRequest.getSenderUserId())) {
            LOG.info(Constant.USER_NOT_LOGIN_MESSAGE, requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.USER_NOT_LOGIN_MESSAGE));
        }

        if (requestVO.getMethod().equals(Constant.METHOD_DELETE_FRIEND)) {
            handleDelete(friendRequest);
        } else {
            handleAdd(friendRequest);
        }
        return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_SUCCESS, true, null));
    }

    private void handleDelete(WsFriendRequest friendRequest) {
        userService.deleteFriend(friendRequest.getSenderUserId(), friendRequest.getReceiverUserId());
        sessionService.deleteSessionByUserIdFriendId(friendRequest.getSenderUserId(), friendRequest.getReceiverUserId());
        noticeReceiverUserToRemoveFriendTag(friendRequest.getSenderUserId(), friendRequest.getReceiverUserId());
    }

    private void noticeReceiverUserToRemoveFriendTag(int senderUserId, int receiverUserId) {
        ChannelHandlerContext ctx = websocketChannelManager.getChannel(receiverUserId);
        if (ctx != null) {
            WsFriendResponse friendResponse = new WsFriendResponse();
            friendResponse.setFriendId(senderUserId);
            ResponseVO responseVO = ResponseVO.create(Constant.METHOD_DELETE_FRIEND, true, friendResponse);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(responseVO));
            ctx.writeAndFlush(textWebSocketFrame);
        }
    }

    private void handleAdd(WsFriendRequest friendRequest) {
        ChannelHandlerContext ctx = websocketChannelManager.getChannel(friendRequest.getReceiverUserId());
        if (ctx != null) {
            WsFriendResponse friendResponse = new WsFriendResponse();
            friendResponse.setFriendId(friendRequest.getSenderUserId());
            UserVO userVO = userService.getUserInfo(friendRequest.getSenderUserId());
            friendResponse.setFriendNickname((userVO == null) ? "" : userVO.getUserNickname());
            friendResponse.setAvatar((userVO == null) ? "" : userVO.getAvatar());

            ResponseVO responseVO = ResponseVO.create(Constant.METHOD_ADD_FRIEND, true, friendResponse);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(responseVO));
            ctx.writeAndFlush(textWebSocketFrame);
        }
    }
}
