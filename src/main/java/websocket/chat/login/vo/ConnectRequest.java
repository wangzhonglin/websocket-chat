package websocket.chat.login.vo;

/**
 * ConnectRequest
 * Date: 2016-04-04
 *
 * @author wangzhonglin
 */
public class ConnectRequest {
    private int userId;
    private int status;

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
