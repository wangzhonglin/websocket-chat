package websocket.chat.user.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.user.vo.FriendVO;

import java.util.List;

/**
 * FriendDao
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
@Dao
public interface FriendDao {

    int insert(@Param("friend") FriendVO friendVO);

    int delete(@Param("userId") int userId, @Param("friendId") int friendId);

    List<Integer> getReceiverUserIdListBySenderUserId(@Param("senderUserId") int senderUserId);

    List<Integer> getSenderUserIdListByReceiverUserId(@Param("receiverUserId") int receiverUserId);

    FriendVO getFriend(@Param("senderUserId") int senderUserId, @Param("receiverUserId") int receiverUserId);
}
