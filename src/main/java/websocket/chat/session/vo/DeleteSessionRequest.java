package websocket.chat.session.vo;

/**
 * DeleteSessionRequest
 * Date: 2016-04-14
 *
 * @author wangzhonglin
 */
public class DeleteSessionRequest {
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
