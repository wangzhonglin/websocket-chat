package websocket.chat.user.service;

import websocket.chat.user.vo.UserVO;

/**
 * TODO: description
 * Date: 2016-01-08
 *
 * @author wangzhonglin
 */
public interface UserService {
    int getUserId();

    UserVO getUserInfo(int userId);

    int createUser(UserVO userVO);

    int addFriend(int senderUserId, int receiverUserId);
}
