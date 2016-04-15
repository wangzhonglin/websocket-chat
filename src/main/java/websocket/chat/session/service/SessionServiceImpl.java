package websocket.chat.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import websocket.chat.constant.SessionStatusEnum;
import websocket.chat.message.service.MessageService;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.session.dao.SessionDao;
import websocket.chat.session.vo.LatestSessionListResponse;
import websocket.chat.session.vo.SessionVO;
import websocket.chat.user.service.UserService;
import websocket.chat.user.vo.UserVO;

import java.util.ArrayList;
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

        List<SessionVO> sessionList = getSessionListByUserId(userId);
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

            MessageVO messageVO = messageService.getLastMessage(sessionVO.getId());
            if (messageVO == null) {
                eachSession.setLastMessageContent("");
                eachSession.setLastMessageTime(0L);
                eachSession.setUnreadMsgCount(0);
            } else {
                eachSession.setLastMessageContent(messageVO.getContent());
                eachSession.setLastMessageTime(messageVO.getCreateTime().getTime());
                eachSession.setUnreadMsgCount(messageService.getUnreadMsgCount(sessionVO.getId()));
            }
        }
        return response;
    }

    @Override
    public int deleteSession(int sessionId) {
        return sessionDao.updateSessionStatusById(sessionId, SessionStatusEnum.CLOSED.value);
    }
}
