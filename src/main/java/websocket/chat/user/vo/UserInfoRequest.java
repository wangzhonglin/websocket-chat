package websocket.chat.user.vo;

/**
 * UserInfoRequest
 * Date: 2016-04-20
 *
 * @author wangzhonglin
 */
public class UserInfoRequest {
    private String loginSessionId;
    private int userId;

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
}
