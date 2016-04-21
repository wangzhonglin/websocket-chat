package websocket.chat.session.vo;

/**
 * RecentSessionRequest
 * Date: 2016-04-21
 *
 * @author wangzhonglin
 */
public class RecentSessionRequest {
    private String loginSessionId;
    private int sessionId;
    private int userId;

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
