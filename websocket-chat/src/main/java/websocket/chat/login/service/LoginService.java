package websocket.chat.login.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.constant.UserStatusEnum;
import websocket.chat.login.dao.LoginDao;
import websocket.chat.login.vo.LoginResponse;
import websocket.chat.login.vo.LoginSessionVO;
import websocket.chat.login.vo.LoginVO;
import websocket.chat.user.dao.UserDao;
import websocket.chat.user.vo.UserVO;

import java.util.Date;

/**
 * Login Service
 * Date: 2016-03-21
 *
 * @author wangzhonglin
 */
@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private UserDao userDao;

    public LoginResponse login(LoginVO loginVO) {
        if (loginVO == null) {
            return null;
        }

        UserVO userVO = userDao.getUserById(loginVO.getUserId());
        if (userVO == null || !userVO.getPassword().equals(loginVO.getPassword())) {
            return null;
        }

        LoginSessionVO loginSessionVO = loginDao.getLoginSessionByUserId(userVO.getId());
        String sessionId = makeRandomSessionId();
        if (loginSessionVO == null) {
            loginSessionVO = new LoginSessionVO();
            loginSessionVO.setUserId(userVO.getId());
            loginSessionVO.setSessionId(sessionId);
            loginSessionVO.setStatus(UserStatusEnum.OFFLINE.value);
            Date currDate = new Date();
            loginSessionVO.setCreateTime(currDate);
            loginSessionVO.setUpdateTime(currDate);
            loginSessionVO.setDelFlag(DeleteFlagEnum.OK.value);
            loginDao.insert(loginSessionVO);
        } else {
            loginDao.updateSessionIdByUserId(userVO.getId(), sessionId);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(userVO.getId());
        loginResponse.setName(userVO.getUserName());
        loginResponse.setNickname(userVO.getUserNickname());
        loginResponse.setSessionId(loginSessionVO.getSessionId());
        return loginResponse;
    }

    private String makeRandomSessionId() {
        String sessionId;
        while (true) {
            sessionId = RandomStringUtils.randomAlphanumeric(Constant.SESSION_ID_LENGTH);
            LoginSessionVO loginSessionVO = loginDao.getLoginSessionBySessionId(sessionId);
            if (loginSessionVO == null) {
                return sessionId;
            }
        }
    }

    public void updateUserStatus(int userId, UserStatusEnum status) {
        loginDao.updateStatusByUserId(userId, status.value);
    }

    public boolean checkLogin(String sessionId, int userId) {
        if (sessionId == null) {
            return false;
        }
        LoginSessionVO loginSessionVO = loginDao.getLoginSessionBySessionId(sessionId);
        return !(loginSessionVO == null || loginSessionVO.getUserId() != userId);
    }
}
