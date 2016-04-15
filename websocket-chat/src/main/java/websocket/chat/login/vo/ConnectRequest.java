package websocket.chat.login.vo;

/**
 * ConnectRequest
 * Date: 2016-04-04
 *
 * @author wangzhonglin
 */
public class ConnectRequest {
    private String sessionId;
    private int userId;
    private int status;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
