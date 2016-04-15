package websocket.chat.user;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import websocket.chat.BaseController;
import websocket.chat.constant.Constant;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.AddFriendRequest;
import websocket.chat.util.ApiResponse;

/**
 * 用户管理控制器
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("api/addFriend")
    public String addFriend(@Param("d") String d, @Param("cb") String cb) {
        AddFriendRequest addFriendRequest = JSON.parseObject(d, AddFriendRequest.class);
        if (addFriendRequest == null) {
            ApiResponse apiResponse = ApiResponse.create(false, "添加好友失败, 参数为空！", Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(addFriendRequest.getSessionId(), addFriendRequest.getSenderUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, "添加好友失败, 用户未登录！", Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        userService.addFriend(addFriendRequest.getSenderUserId(), addFriendRequest.getReceiverUserId());
        ApiResponse apiResponse = ApiResponse.create(true, "SUCCESS", Constant.SUCCESS_CODE, null);
        return toJson(apiResponse, cb);
    }
}
