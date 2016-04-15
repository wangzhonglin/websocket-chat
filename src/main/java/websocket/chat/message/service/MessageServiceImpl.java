package websocket.chat.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.message.dao.MessageDao;
import websocket.chat.message.vo.MessageVO;

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
    public int getUnreadMsgCount(int sessionId) {
        int unreadMsgCount = messageDao.getUnreadMsgCount(sessionId);
        if (unreadMsgCount > 0) {
            return unreadMsgCount;
        } else {
            return 0;
        }
    }
}
