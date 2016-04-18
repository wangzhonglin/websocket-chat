package websocket.chat.message.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import websocket.chat.BaseController;
import websocket.chat.constant.Constant;
import websocket.chat.constant.MessageStatusEnum;
import websocket.chat.message.service.MessageService;
import websocket.chat.message.vo.HistoryMessageListRequest;
import websocket.chat.message.vo.HistoryMessageListResponse;
import websocket.chat.message.vo.SessionMessageStatusRequest;
import websocket.chat.util.ApiResponse;
import websocket.chat.util.JsonUtil;

/**
 * MessageController
 * Date: 2016-04-15
 *
 * @author wangzhonglin
 */
@RestController
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("api/getHistoryMessageList")
    public String getHistoryMessageList(@Param("d") String d, @Param("cb") String cb) {
        HistoryMessageListRequest request = JsonUtil.safelyParseObject(d, HistoryMessageListRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        HistoryMessageListResponse response = messageService.getHistoryMessageResponse(request.getSessionId(), request.getLimit(), request.getLastMessageId());
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, response);
        return toJson(apiResponse, cb);
    }

    @RequestMapping("api/updateSessionMessageStatus")
    public String updateSessionMessageStatus(@Param("d") String d, @Param("cb") String cb) {
        SessionMessageStatusRequest request = JsonUtil.safelyParseObject(d, SessionMessageStatusRequest.class);
        if (request == null) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.NULL_PARAM_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }
        if (!checkLogin(request.getLoginSessionId(), request.getUserId())) {
            ApiResponse apiResponse = ApiResponse.create(false, Constant.USER_NOT_LOGIN_MESSAGE, Constant.ERROR_CODE, null);
            return toJson(apiResponse, cb);
        }

        messageService.updateMessageStatus(request.getSessionId(), MessageStatusEnum.READ.value);
        ApiResponse apiResponse = ApiResponse.create(true, Constant.SUCCESS_MESSAGE, Constant.SUCCESS_CODE, null);
        return toJson(apiResponse, cb);
    }
}
