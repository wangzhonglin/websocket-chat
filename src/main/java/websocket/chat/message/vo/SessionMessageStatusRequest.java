package websocket.chat.message.vo;

/**
 * SessionMessageStatusRequest
 * Date: 2016-04-18
 *
 * @author wangzhonglin
 */
public class SessionMessageStatusRequest {
    private String loginSessionId;
    private int userId;
    private int sessionId;

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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
