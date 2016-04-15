package websocket.chat.message.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.constant.UserStatusEnum;
import websocket.chat.login.service.LoginService;
import websocket.chat.login.vo.ConnectRequest;
import websocket.chat.util.JsonUtil;
import websocket.chat.websocket.manage.WebsocketChannelManager;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

/**
 * MessageConnectService
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Service
public class MessageConnectService extends MessageBaseService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageConnectService.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private WebsocketChannelManager websocketChannelManager;

    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx){
        ConnectRequest connectRequest = JsonUtil.safelyParseObject(requestVO.getRequest(), ConnectRequest.class);
        if (connectRequest == null) {
            LOG.info("Cannot login, bad request. {}", requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_LOGIN, false, Constant.NULL_PARAM_MESSAGE));
        }

        UserStatusEnum status = UserStatusEnum.byte2Enum((byte) connectRequest.getStatus());
        if (status == null) {
            LOG.info("Wrong type of status, requestVO={}", requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_LOGIN, false, Constant.NULL_PARAM_MESSAGE));
        }

        if (!loginService.checkLogin(requestVO.getLoginSessionId(), connectRequest.getUserId())) {
            LOG.info("验证sessionId, userId失败, requestVO={}", requestVO);
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_LOGIN, false, Constant.USER_NOT_LOGIN_MESSAGE));
        }

        loginService.updateUserStatus(connectRequest.getUserId(), status);
        websocketChannelManager.socketCreated(connectRequest.getUserId(), ctx);
        return JSON.toJSONString(ResponseVO.create(Constant.METHOD_LOGIN, true, Constant.SUCCESS_MESSAGE));
    }
}
