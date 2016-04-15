package websocket.chat.login.vo;

/**
 * 登录后返回对象
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
public class LoginResponse {
    private int userId;
    private String sessionId;
    private String name;
    private String nickname;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
