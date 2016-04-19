package websocket.chat.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import websocket.chat.message.dao.MessageDao;
import websocket.chat.message.vo.HistoryMessageListResponse;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageServiceImpl
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserService userService;

    @Override
    public int createMessage(MessageVO messageVO) {
        if (messageVO == null) {
            return 0;
        }
        messageDao.insert(messageVO);
        return messageVO.getId();
    }

    @Override
    public MessageVO getLastMessage(int sessionId) {
        return messageDao.getLastMessageBySessionId(sessionId);
    }

    @Override
    public int getUnreadMsgCount(int sessionId, int receiverUserId) {
        int unreadMsgCount = messageDao.getUnreadMsgCount(sessionId, receiverUserId);
        if (unreadMsgCount > 0) {
            return unreadMsgCount;
        } else {
            return 0;
        }
    }

    @Override
    public HistoryMessageListResponse getHistoryMessageResponse(int sessionId, int limit, int lastMessageId, int existMsgNum) {
        HistoryMessageListResponse response = new HistoryMessageListResponse();
        List<HistoryMessageListResponse.EachHistoryMessage> eachHistoryMessageList = new ArrayList<>();
        response.setHistoryMessageList(eachHistoryMessageList);

        if (limit <= 0 || lastMessageId < 0) {
            response.setLastMessageId(0);
            return response;
        }

        if (existMsgNum > 0) {
            limit += existMsgNum;
        }

        List<MessageVO> messageList;
        if (lastMessageId == 0) {
            messageList = messageDao.getHistoryMessageListWithoutLastMessageId(sessionId, limit);
        } else {
            messageList = messageDao.getHistoryMessageList(sessionId, limit, lastMessageId);
        }
        if (CollectionUtils.isEmpty(messageList)) {
            response.setLastMessageId(0);
            return response;
        }
        messageList = messageList.subList(existMsgNum, messageList.size());
        if (CollectionUtils.isEmpty(messageList)) {
            response.setLastMessageId(0);
            return response;
        }

        for (MessageVO messageVO : messageList) {
            HistoryMessageListResponse.EachHistoryMessage eachHistoryMessage = new HistoryMessageListResponse.EachHistoryMessage();
            eachHistoryMessage.setUserId(messageVO.getSenderUserId());
            UserVO userVO = userService.getUserInfo(messageVO.getSenderUserId());
            eachHistoryMessage.setUserNickname(userVO.getUserNickname());
            eachHistoryMessage.setContent(messageVO.getContent());
            eachHistoryMessage.setMessageType(messageVO.getMessageType());
            eachHistoryMessage.setMessageTime(messageVO.getCreateTime().getTime());
            eachHistoryMessageList.add(eachHistoryMessage);
        }

        response.setLastMessageId(messageList.get(messageList.size() - 1).getId());
        return response;
    }

    @Override
    public int updateMessageStatus(int sessionId, int receiverUserId, int status) {
        return messageDao.updateMessageStatusBySessionIdSenderUserId(sessionId, receiverUserId, status);
    }
}
