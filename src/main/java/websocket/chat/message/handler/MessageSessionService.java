package websocket.chat.message.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import websocket.chat.constant.Constant;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.constant.SessionStatusEnum;
import websocket.chat.message.vo.WsSessionRequest;
import websocket.chat.message.vo.WsSessionResponse;
import websocket.chat.session.service.SessionService;
import websocket.chat.session.vo.SessionVO;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;
import websocket.chat.util.JsonUtil;
import websocket.chat.websocket.manage.WebsocketChannelManager;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MessageSessionService
 * Date: 2016-04-17
 *
 * @author wangzhonglin
 */
@Service
public class MessageSessionService extends MessageBaseService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSessionService.class);

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebsocketChannelManager websocketChannelManager;

    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx) {
        WsSessionRequest sessionRequest = JsonUtil.safelyParseObject(requestVO.getRequest(), WsSessionRequest.class);
        if (sessionRequest == null) {
            LOG.info(Constant.NULL_PARAM_MESSAGE, requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.NULL_PARAM_MESSAGE));
        }
        if (!checkLogin(requestVO.getLoginSessionId(), sessionRequest.getSenderUserId())) {
            LOG.info(Constant.USER_NOT_LOGIN_MESSAGE, requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.USER_NOT_LOGIN_MESSAGE));
        }
        List<SessionVO> sessionList = sessionService.getSessionListByUserIdFriendId(sessionRequest.getSenderUserId(), sessionRequest.getReceiverUserId());
        int sessionId;
        if (!CollectionUtils.isEmpty(sessionList)) {
            sessionList = sessionList.stream().filter(sessionVO -> sessionVO.getStatus() == SessionStatusEnum.SUCCESS.value)
                    .sorted((a, b) -> Integer.compare(b.getId(), a.getId()))
                    .collect(Collectors.toList());
            SessionVO sessionWithBiggestId = sessionList.stream().findFirst().get();
            sessionList.stream().filter(sessionVO -> sessionVO.getId() != sessionWithBiggestId.getId()).forEach(sessionVO -> sessionService.deleteSession(sessionVO.getId()));
            sessionId = sessionWithBiggestId.getId();
        } else {
            SessionVO sessionVO = new SessionVO();
            sessionVO.setSenderUserId(sessionRequest.getSenderUserId());
            sessionVO.setReceiverUserId(sessionRequest.getReceiverUserId());
            sessionVO.setStatus(SessionStatusEnum.SUCCESS.value);
            Date currDate = new Date();
            sessionVO.setCreateTime(currDate);
            sessionVO.setUpdateTime(currDate);
            sessionVO.setDelFlag(DeleteFlagEnum.OK.value);
            sessionId = sessionService.createSession(sessionVO);
        }
        WsSessionResponse sessionResponse = new WsSessionResponse();
        sessionResponse.setSessionId(sessionId);
        sessionResponse.setFriendId(sessionRequest.getReceiverUserId());
        UserVO userVO = userService.getUserInfo(sessionRequest.getReceiverUserId());
        sessionResponse.setFriendNickname((userVO == null) ? "" : userVO.getUserNickname());
        sessionResponse.setAvatar((userVO == null) ? Constant.DEFAULT_USER_AVATAR : userVO.getAvatar());
        sessionResponse.setActive(true);

        noticeReceiverUserToCreateSession(sessionId, sessionRequest.getSenderUserId(), sessionRequest.getReceiverUserId());
        return JSON.toJSONString(ResponseVO.create(Constant.METHOD_CREATE_SESSION, true, sessionResponse));
    }

    private void noticeReceiverUserToCreateSession(int sessionId, int senderUserId, int receiverUserId) {
        ChannelHandlerContext ctx = websocketChannelManager.getChannel(receiverUserId);
        if (ctx != null) {
            WsSessionResponse sessionResponse = new WsSessionResponse();
            sessionResponse.setSessionId(sessionId);
            sessionResponse.setFriendId(senderUserId);
            UserVO userVO = userService.getUserInfo(senderUserId);
            sessionResponse.setFriendNickname((userVO == null) ? "" : userVO.getUserNickname());
            sessionResponse.setAvatar((userVO == null) ? Constant.DEFAULT_USER_AVATAR : userVO.getAvatar());
            sessionResponse.setActive(false);

            ResponseVO responseVO = ResponseVO.create(Constant.METHOD_CREATE_SESSION, true, sessionResponse);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(responseVO));
            ctx.writeAndFlush(textWebSocketFrame);
        }
    }
}
