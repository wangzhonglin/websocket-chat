package websocket.chat.message.handler;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.login.service.LoginService;
import websocket.chat.websocket.vo.RequestVO;

/**
 * Basic message service interface
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Service
public abstract class MessageBaseService {
    @Autowired
    private LoginService loginService;

    /**
     * Execute processing message
     */
    public abstract String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx);

    protected boolean checkLogin(String loginSessionId, int userId) {
        return loginService.checkLogin(loginSessionId, userId);
    }
}
