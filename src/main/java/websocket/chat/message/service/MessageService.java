package websocket.chat.message.service;

import websocket.chat.message.vo.HistoryMessageListResponse;
import websocket.chat.message.vo.MessageVO;

/**
 * MessageService
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
public interface MessageService {

    int createMessage(MessageVO messageVO);

    MessageVO getLastMessage(int sessionId);

    int getUnreadMsgCount(int sessionId, int receiverUserId);

    HistoryMessageListResponse getHistoryMessageResponse(int sessionId, int limit, int lastMessageId, int existMsgNum);

    int updateMessageStatus(int sessionId, int receiverUserId, int status);
}
