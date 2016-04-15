package websocket.chat.user.vo;

import java.util.List;

/**
 * FriendListResponse
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class FriendListResponse {
    private List<EachUser> friendList;

    public static EachUser createEachUser(UserVO userVO) {
        EachUser eachUser = new EachUser();
        eachUser.setUserId(userVO.getId());
        eachUser.setUserName(userVO.getUserName());
        eachUser.setUserNickname(userVO.getUserNickname());
        return eachUser;
    }

    public List<EachUser> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<EachUser> friendList) {
        this.friendList = friendList;
    }

    public static class EachUser {
        private int userId;
        private String userName;
        private String userNickname;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }
    }
}
