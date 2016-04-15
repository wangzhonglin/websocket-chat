package websocket.chat.session.vo;

/**
 * LatestSessionListRequest
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class LatestSessionListRequest {
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
