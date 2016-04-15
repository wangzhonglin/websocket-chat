package websocket.chat.session.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import websocket.chat.BaseController;
import websocket.chat.constant.Constant;
import websocket.chat.session.service.SessionService;
import websocket.chat.session.vo.DeleteSessionRequest;
import websocket.chat.session.vo.LatestSessionListRequest;
import websocket.chat.session.vo.LatestSessionListResponse;
import websocket.chat.util.ApiResponse;
import websocket.chat.util.JsonUtil;

/**
 * 会话控制器
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@RestController
public class SessionController extends BaseController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping("api/getLatestSessionList")
    public String getLatestSessionList(@Param("d") String d, @Param("cb") String cb) {
        LatestSessionListRequest request = JsonUtil.safelyParseObject(d, LatestSessionListRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        LatestSessionListResponse response = sessionService.getLatestSessionList(request.getUserId());
        ApiResponse<LatestSessionListResponse> apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, response);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/deleteSession")
    public String deleteSession(@Param("d") String d, @Param("cb") String cb) {
        DeleteSessionRequest request = JsonUtil.safelyParseObject(d, DeleteSessionRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        sessionService.deleteSession(request.getSessionId());
        ApiResponse<LatestSessionListResponse> apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, null);
        return toJson(apiResponse, cb);
    }
}
