package websocket.chat.session.dao;

import org.apache.ibatis.annotations.Param;
import websocket.chat.database.Dao;
import websocket.chat.session.vo.RecentSessionVO;

import java.util.List;

/**
 * RecentSessionDao
 * Date: 2016-04-21
 *
 * @author wangzhonglin
 */
@Dao
public interface RecentSessionDao {

    int insert(@Param("recent") RecentSessionVO recentSessionVO);

    List<RecentSessionVO> getRecentSessionByUserId(@Param("userId") int userId);

    int delete(@Param("sessionId") int sessionId, @Param("userId") int userId);
}
