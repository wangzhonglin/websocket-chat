package websocket.chat.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.login.vo.RegisterVO;
import websocket.chat.user.dao.FriendDao;
import websocket.chat.user.dao.UserDao;
import websocket.chat.user.vo.FriendVO;
import websocket.chat.user.vo.UserVO;

import java.util.Date;

/**
 * UserServiceImpl
 * Date: 2016-01-08
 *
 * @author wangzhonglin
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendDao friendDao;

    @Override
    public int getUserId() {
        return 1;
    }

    @Override
    public UserVO getUserInfo(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public int createUser(UserVO userVO) {
        if (userVO == null) {
            return 0;
        }
        return userDao.createUser(userVO);
    }

    @Override
    public int addFriend(int senderUserId, int receiverUserId) {
        if (senderUserId <= 0 || receiverUserId <=0 ) {
            return 0;
        }
        UserVO senderUser = userDao.getUserById(receiverUserId);
        UserVO receiverUser = userDao.getUserById(receiverUserId);
        if (senderUser == null || receiverUser == null) {
            return 0;
        }

        FriendVO friendVO = new FriendVO();
        friendVO.setSenderUserId(senderUserId);
        friendVO.setReceiverUserId(receiverUserId);
        Date currDate = new Date();
        friendVO.setCreateTime(currDate);
        friendVO.setUpdateTime(currDate);
        friendVO.setDelFlag(DeleteFlagEnum.OK.value);
        return friendDao.insert(friendVO);
    }
}
