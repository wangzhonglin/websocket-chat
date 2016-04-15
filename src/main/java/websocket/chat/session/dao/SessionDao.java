package websocket.chat.session.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.session.vo.SessionVO;

import java.util.List;

/**
 * SessionDao
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@Dao
public interface SessionDao {

    int insert(@Param("session") SessionVO sessionVO);

    SessionVO getSessionById(@Param("id") int id);

    List<SessionVO> getSessionListByUserId(@Param("userId") int userId);

    int updateSessionStatusById(@Param("id") int id, @Param("status") int status);
}
