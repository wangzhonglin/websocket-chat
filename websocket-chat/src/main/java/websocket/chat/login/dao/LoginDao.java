package websocket.chat.login.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.login.vo.LoginSessionVO;

/**
 * LoginDao
 * Date: 2016-03-21
 *
 * @author wangzhonglin
 */
@Dao
public interface LoginDao {

    int updateStatusByUserId(@Param("userId") int userId, @Param("status") int status);

    int updateSessionIdByUserId(@Param("userId") int userId, @Param("sessionId") String sessionId);

    LoginSessionVO getLoginSessionByUserId(@Param("userId") int userId);

    int insert(@Param("loginSession") LoginSessionVO loginSession);

    LoginSessionVO getLoginSessionBySessionId(@Param("sessionId") String sessionId);
}
