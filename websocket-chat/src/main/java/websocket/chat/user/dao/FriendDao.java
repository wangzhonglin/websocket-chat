package websocket.chat.user.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.user.vo.FriendVO;

/**
 * FriendDao
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
@Dao
public interface FriendDao {

    int insert(@Param("fiend") FriendVO friendVO);
}
