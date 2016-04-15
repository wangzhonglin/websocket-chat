package websocket.chat.message.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.message.vo.MessageVO;

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
}
