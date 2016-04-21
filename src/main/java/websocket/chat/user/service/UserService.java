package websocket.chat.user.service;

import websocket.chat.user.vo.FriendListResponse;
import websocket.chat.user.vo.UpdateUserInfoRequest;
import websocket.chat.user.vo.UserListResponse;
import websocket.chat.user.vo.UserVO;

/**
 * UserService
 * Date: 2016-01-08
 *
 * @author wangzhonglin
 */
public interface UserService {
    int getUserId();

    UserVO getUserInfo(int userId);

    int createUser(UserVO userVO);

    int addFriend(int senderUserId, int receiverUserId);

    int deleteFriend(int userId, int friendId);

    UserListResponse searchUser(String keyword);

    FriendListResponse getFriendListByUserId(int userId);

    int updateUserInfo(int userId, String userName, String userNickname, String password,
                       int sex, String signature, String avatar);
}
