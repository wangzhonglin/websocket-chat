package websocket.chat.user.vo;

import java.util.List;

/**
 * UserListResponse
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class UserListResponse {
    private List<EachUser> userList;

    public static EachUser createEachUser(UserVO userVO) {
        EachUser eachUser = new EachUser();
        eachUser.setUserId(userVO.getId());
        eachUser.setUserName(userVO.getUserName());
        eachUser.setUserNickname(userVO.getUserNickname());
        eachUser.setSex(userVO.getSex());
        eachUser.setSignature(userVO.getSignature());
        eachUser.setAvatar(userVO.getAvatar());
        return eachUser;
    }

    public List<EachUser> getUserList() {
        return userList;
    }

    public void setUserList(List<EachUser> userList) {
        this.userList = userList;
    }

    public static class EachUser {
        private int userId;
        private String userName;
        private String userNickname;
        private int sex;
        private String signature;
        private String avatar;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
