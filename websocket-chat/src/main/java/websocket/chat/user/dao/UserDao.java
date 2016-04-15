package websocket.chat.user.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.user.vo.UserVO;

/**
 * UserDao
 * Date: 2016-01-08
 *
 * @author wangzhonglin
 */
@Dao
public interface UserDao {
    UserVO getUserById(@Param("userId") int userId);

    int createUser(@Param("user") UserVO userVO);
}
