package websocket.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import websocket.chat.constant.Constant;
import websocket.chat.login.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基本控制器
 * Date: 2016-04-02
 *
 * @author wangzhonglin
 */
@RestController
public class BaseController {

    @Autowired
    private LoginService loginService;

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getResponse();
    }

    protected static String toJson(Object object) {
        return Constant.JSON_CALLBACK + "(" + JSON.toJSONString(object) + ")";
    }

    protected static String toJson(Object object, String JSON_CALLBACK) {
        return JSON_CALLBACK + "(" + JSON.toJSONString(object) + ")";
    }

    protected boolean checkLogin(String loginSessionId, int userId) {
        return loginService.checkLogin(loginSessionId, userId);
    }

}
