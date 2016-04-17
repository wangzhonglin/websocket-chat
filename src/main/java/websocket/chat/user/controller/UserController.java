package websocket.chat.user.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import websocket.chat.BaseController;
import websocket.chat.constant.Constant;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.*;
import websocket.chat.util.ApiResponse;
import websocket.chat.util.JsonUtil;

/**
 * 用户管理控制器
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("api/addFriend")
    public String addFriend(@Param("d") String d, @Param("cb") String cb) {
        AddFriendRequest request = JsonUtil.safelyParseObject(d, AddFriendRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getSenderUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        userService.addFriend(request.getSenderUserId(), request.getReceiverUserId());
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, null);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/deleteFriend")
    public String deleteFriend(@Param("d") String d, @Param("cb") String cb) {
        DeleteFriendRequest request = JsonUtil.safelyParseObject(d, DeleteFriendRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        userService.deleteFriend(request.getUserId(), request.getFriendId());
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, null);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/getFriendList")
    public String getFriendList(@Param("d") String d, @Param("cb") String cb) {
        FriendListRequest request = JsonUtil.safelyParseObject(d, FriendListRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        FriendListResponse response = userService.getFriendListByUserId(request.getUserId());
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, response);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/searchUser")
    public String searchUser(@Param("d") String d, @Param("cb") String cb) {
        SearchUserRequest request = JsonUtil.safelyParseObject(d, SearchUserRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        UserListResponse response = userService.searchUser(request.getKeyword());
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, response);
        return toJson(apiResponse, cb);
    }
}
