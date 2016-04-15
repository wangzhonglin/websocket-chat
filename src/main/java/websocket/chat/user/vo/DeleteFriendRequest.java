package websocket.chat.user.vo;

/**
 * DeleteFriendRequest
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class DeleteFriendRequest {
    private String loginSessionId;
    private int userId;
    private int friendId;

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
