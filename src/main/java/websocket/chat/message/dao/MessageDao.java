package websocket.chat.message.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.message.vo.MessageVO;

import java.util.List;

/**
 * MessageDao
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@Dao
public interface MessageDao {

    int insert(@Param("message") MessageVO messageVO);

    MessageVO getLastMessageBySessionId(@Param("sessionId") int sessionId);

    int getUnreadMsgCount(@Param("sessionId") int sessionId);

    List<MessageVO> getHistoryMessageList(@Param("sessionId") int sessionId, @Param("limit") int limit, @Param("lastMessageId") int lastMessageId);

    List<MessageVO> getHistoryMessageListWithoutLastMessageId(@Param("sessionId") int sessionId, @Param("limit") int limit);

    int updateMessageStatusBySessionId(@Param("sessionId") int sessionId, @Param("status") int status);
}
