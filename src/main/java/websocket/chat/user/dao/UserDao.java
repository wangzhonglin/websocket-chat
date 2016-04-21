package websocket.chat.user.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.user.vo.UserVO;

import java.util.List;

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

    List<UserVO> getUsersByUserNickname(@Param("userNickname") String userNickname);

    int updateUserInfo(@Param("userId") int userId, @Param("userName") String userName, @Param("userNickname") String userNickname,
                       @Param("password") String password, @Param("sex") int sex, @Param("signature") String signature,
                       @Param("avatar") String avatar);
}
