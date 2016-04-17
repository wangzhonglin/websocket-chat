package websocket.chat.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.user.dao.FriendDao;
import websocket.chat.user.dao.UserDao;
import websocket.chat.user.vo.FriendListResponse;
import websocket.chat.user.vo.FriendVO;
import websocket.chat.user.vo.UserListResponse;
import websocket.chat.user.vo.UserVO;
import websocket.chat.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        userDao.createUser(userVO);
        return userVO.getId();
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

    @Override
    public int deleteFriend(int userId, int friendId) {
        int deleteCount = 0;
        deleteCount += friendDao.delete(userId, friendId);
        deleteCount += friendDao.delete(friendId, userId);
        return deleteCount;
    }

    @Override
    public UserListResponse searchUser(String keyword) {
        UserListResponse response = new UserListResponse();
        List<UserListResponse.EachUser> eachUserList = new ArrayList<>();
        response.setUserList(eachUserList);

        if (StringUtil.isEmpty(keyword)) {
            return response;
        }

        if (!StringUtil.allDigit(keyword)) {
            int userId = StringUtil.str2int(keyword, 0);
            UserVO userVO = userDao.getUserById(userId);
            if (userVO != null) {
                eachUserList.add(UserListResponse.createEachUser(userVO));
            }
        }

        List<UserVO> userList = userDao.getUsersByUserNickname(keyword);
        if (!CollectionUtils.isEmpty(userList)) {
            eachUserList.addAll(userList.stream().map(UserListResponse::createEachUser).collect(Collectors.toList()));
        }
        return response;
    }

    @Override
    public FriendListResponse getFriendListByUserId(int userId) {
        List<Integer> friendIdList = new ArrayList<>();
        List<Integer> senderUserIdList = friendDao.getSenderUserIdListByReceiverUserId(userId);
        List<Integer> receiverUserIdList = friendDao.getReceiverUserIdListBySenderUserId(userId);
        if (!CollectionUtils.isEmpty(senderUserIdList)) {
            friendIdList.addAll(senderUserIdList);
        }
        if (!CollectionUtils.isEmpty(receiverUserIdList)) {
            friendIdList.addAll(receiverUserIdList);
        }

        FriendListResponse response = new FriendListResponse();
        List<FriendListResponse.EachUser> eachUserList = new ArrayList<>();
        response.setFriendList(eachUserList);

        if (CollectionUtils.isEmpty(friendIdList)) {
            return response;
        }
        friendIdList.sort(Integer::compare);
        for (Integer friendId : friendIdList) {
            UserVO userVO = userDao.getUserById(friendId);
            if (userVO != null) {
                FriendListResponse.EachUser eachUser = new FriendListResponse.EachUser();
                eachUserList.add(eachUser);
                eachUser.setUserId(userVO.getId());
                eachUser.setUserName(userVO.getUserName());
                eachUser.setUserNickname(userVO.getUserNickname());
            }
        }

        return response;
    }
}
