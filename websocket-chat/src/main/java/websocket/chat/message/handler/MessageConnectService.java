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
import websocket.chat.util.ApiResponse;
import websocket.chat.util.JsonUtil;
import websocket.chat.websocket.vo.RequestVO;

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

    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx){
        ConnectRequest connectRequest = JsonUtil.safelyParseObject(requestVO.getRequest(), ConnectRequest.class);
        if (connectRequest == null) {
            LOG.info("Cannot login, bad request. {}", requestVO);
            return JSON.toJSONString(ApiResponse.create(false, "参数错误", Constant.ERROR_CODE, null));
        }
        UserStatusEnum status = UserStatusEnum.byte2Enum((byte) connectRequest.getStatus());
        if (status == null) {
            LOG.info("Wrong type of status, requestVO={}", requestVO);
            return JSON.toJSONString(ApiResponse.create(false, "验证status不合法", Constant.ERROR_CODE, null));
        }
        if (!loginService.checkLogin(connectRequest.getSessionId(), connectRequest.getUserId())) {
            LOG.info("验证sessionId, userId失败, requestVO={}", requestVO);
            return JSON.toJSONString(ApiResponse.create(false, "验证sessionId, userId失败", Constant.ERROR_CODE, null));
        }
        loginService.updateUserStatus(connectRequest.getUserId(), status);
        return JSON.toJSONString(ApiResponse.create(false, "SUCCESS", Constant.SUCCESS_CODE, null));
    }
}
