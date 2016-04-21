package websocket.chat.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import websocket.chat.constant.Constant;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.constant.SessionStatusEnum;
import websocket.chat.message.service.MessageService;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.session.dao.RecentSessionDao;
import websocket.chat.session.dao.SessionDao;
import websocket.chat.session.vo.LatestSessionListResponse;
import websocket.chat.session.vo.RecentSessionVO;
import websocket.chat.session.vo.SessionVO;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SessionServiceImpl
 * Date: 2016-04-08
 *
 * @author wangzhonglin
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private RecentSessionDao recentSessionDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public int createSession(SessionVO sessionVO) {
        if (sessionVO == null) {
            return 0;
        }
        sessionDao.insert(sessionVO);
        return sessionVO.getId();
    }

    @Override
    public SessionVO getSessionBySessionId(int sessionId) {
        return sessionDao.getSessionById(sessionId);
    }

    @Override
    public List<SessionVO> getSessionListByUserId(int userId) {
        return sessionDao.getSessionListByUserId(userId);
    }

    @Override
    public LatestSessionListResponse getLatestSessionList(int userId) {
        LatestSessionListResponse response = new LatestSessionListResponse();
        List<LatestSessionListResponse.EachSession> latestSessionList = new ArrayList<>();
        response.setLatestSessionList(latestSessionList);

        List<RecentSessionVO> recentSessionList = getRecentSessionByUserId(userId);
        if (CollectionUtils.isEmpty(recentSessionList)) {
            return response;
        }

        List<SessionVO> sessionList = sessionDao.getSessionListBySessionIdList(
                recentSessionList.stream().map(RecentSessionVO::getSessionId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(sessionList)) {
            return response;
        }

        sessionList = sessionList.stream().filter(sessionVO -> sessionVO.getStatus()!=SessionStatusEnum.CLOSED.value)
                .sorted((a, b) -> Long.compare(b.getUpdateTime().getTime(), a.getUpdateTime().getTime()))
                .collect(Collectors.toList());

        for (SessionVO sessionVO : sessionList) {
            LatestSessionListResponse.EachSession eachSession = new LatestSessionListResponse.EachSession();
            latestSessionList.add(eachSession);
            eachSession.setSessionId(sessionVO.getId());

            int friendId = (sessionVO.getSenderUserId() == userId) ? sessionVO.getReceiverUserId() : sessionVO.getSenderUserId();
            eachSession.setFriendId(friendId);
            UserVO userVO = userService.getUserInfo(friendId);
            eachSession.setFriendNickname((userVO == null) ? "" : userVO.getUserNickname());
            eachSession.setFriendAvatar((userVO == null) ? Constant.DEFAULT_USER_AVATAR : userVO.getAvatar());

            MessageVO messageVO = messageService.getLastMessage(sessionVO.getId());
            if (messageVO == null) {
                eachSession.setLastMessageContent("");
                eachSession.setLastMessageTime(0L);
                eachSession.setUnreadMsgCount(0);
            } else {
                eachSession.setLastMessageContent(messageVO.getContent());
                eachSession.setLastMessageTime(messageVO.getCreateTime().getTime());
                eachSession.setUnreadMsgCount(messageService.getUnreadMsgCount(sessionVO.getId(), userId));
            }
        }
        return response;
    }

    @Override
    public int deleteSession(int sessionId) {
        return sessionDao.updateSessionStatusById(sessionId, SessionStatusEnum.CLOSED.value);
    }

    @Override
    public List<SessionVO> getSessionListByUserIdFriendId(int userId, int friendId) {
        return sessionDao.getSessionListByUserIdFriendId(userId, friendId);
    }

    @Override
    public int deleteSessionByUserIdFriendId(int userId, int friendId) {
        return sessionDao.closeSessionByUserIdFriendId(userId, friendId);
    }

    @Override
    public int createRecentSession(int sessionId, int userId) {
        RecentSessionVO recentSessionVO = new RecentSessionVO();
        recentSessionVO.setSessionId(sessionId);
        recentSessionVO.setUserId(userId);
        Date currDate = new Date();
        recentSessionVO.setCreateTime(currDate);
        recentSessionVO.setUpdateTime(currDate);
        recentSessionVO.setDelFlag(DeleteFlagEnum.OK.value);
        return recentSessionDao.insert(recentSessionVO);
    }

    @Override
    public List<RecentSessionVO> getRecentSessionByUserId(int userId) {
        return recentSessionDao.getRecentSessionByUserId(userId);
    }

    @Override
    public int deleteRecentSession(int sessionId, int userId) {
        return recentSessionDao.delete(sessionId, userId);
    }
}
