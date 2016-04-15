package websocket.chat.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import websocket.chat.BaseController;
import websocket.chat.constant.Constant;
import websocket.chat.login.service.LoginService;
import websocket.chat.login.vo.LoginResponse;
import websocket.chat.login.vo.LoginVO;
import websocket.chat.login.vo.RegisterVO;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;
import websocket.chat.util.ApiResponse;
import websocket.chat.util.JsonUtil;

import java.util.Date;

/**
 * 登录
 * Date: 2016-01-08
 *
 * @author wangzhonglin
 */
@RestController
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("api/login")
    public String login(@Param("d") String d, @Param("cb") String cb) {
        LoginVO loginVO = JsonUtil.safelyParseObject(d, LoginVO.class);
        if (loginVO == null) {
            ApiResponse apiResponse = ApiResponse.create(false, "登录失败, 参数为空！", Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        LoginResponse loginResponse = loginService.login(loginVO);
        if (loginResponse == null) {
            ApiResponse apiResponse = ApiResponse.create(false, "登录失败, 用户密码错误！", Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        ApiResponse apiResponse = ApiResponse.create(true, "SUCCESS", Constant.SUCCESS_CODE, loginResponse);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/register")
    public String register(@Param("d") String d, @Param("cb") String cb) {
        RegisterVO registerVO = JsonUtil.safelyParseObject(d, RegisterVO.class);
        if (registerVO == null) {
            ApiResponse apiResponse = ApiResponse.create(false, "注册失败, 参数为空！", Constant.ERROR_CODE, null);
            return toJson(apiResponse);
        }
        UserVO userVO = new UserVO();
        userVO.setPassword(registerVO.getPassword());
        userVO.setUserName(registerVO.getUserName());
        userVO.setUserNickname(registerVO.getUserNickname());
        Date currDate = new Date();
        userVO.setCreateTime(currDate);
        userVO.setUpdateTime(currDate);
        userVO.setDelFlag(0);
        int userId = userService.createUser(userVO);
        JSONObject rsp = new JSONObject();
        rsp.put("userId", userId);
        ApiResponse apiResponse = ApiResponse.create(true, "SUCCESS", Constant.SUCCESS_CODE, rsp);
        return toJson(apiResponse, cb);
    }

}
