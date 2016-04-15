package websocket.chat.message.service;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.constant.UserStatusEnum;
import websocket.chat.login.service.LoginService;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.message.vo.WsMessageResponse;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;
import websocket.chat.websocket.manage.WebsocketChannelManager;
import websocket.chat.websocket.vo.ResponseVO;

/**
 * PushWebsocketMessage
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@Service
public class PushWebsocketMessage {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebsocketChannelManager websocketChannelManager;

    public void pushMessage2User(MessageVO messageVO) {
        UserStatusEnum userStatus = loginService.getUserOnlineStatus(messageVO.getReceiverUserId());
        if (userStatus != null && userStatus != UserStatusEnum.OFFLINE) {
            ChannelHandlerContext ctx = websocketChannelManager.getChannel(messageVO.getReceiverUserId());
            if (ctx != null) {
                ResponseVO responseVO = ResponseVO.create(Constant.METHOD_PUSH, true, buildWsMessageResponse(messageVO));
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(responseVO));
                ctx.writeAndFlush(textWebSocketFrame);
            }
        }
    }

    private WsMessageResponse buildWsMessageResponse(MessageVO messageVO) {
        WsMessageResponse wsMessageResponse = new WsMessageResponse();
        wsMessageResponse.setSessionId(messageVO.getSessionId());
        wsMessageResponse.setFriendId(messageVO.getSenderUserId());
        UserVO userVO = userService.getUserInfo(messageVO.getSenderUserId());
        wsMessageResponse.setFriendNickname((userVO == null) ? "" : userVO.getUserNickname());
        wsMessageResponse.setMessageType(messageVO.getMessageType());
        wsMessageResponse.setContent(messageVO.getContent());
        wsMessageResponse.setStatus(messageVO.getStatus());
        wsMessageResponse.setMessageTime(messageVO.getCreateTime().getTime());
        return wsMessageResponse;
    }
}
