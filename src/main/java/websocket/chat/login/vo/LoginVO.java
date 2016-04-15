package websocket.chat.login.vo;

/**
 * LoginVO
 * Date: 2016-03-21
 *
 * @author wangzhonglin
 */
public class LoginVO {
    private int userId;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
