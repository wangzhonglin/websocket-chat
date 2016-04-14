package websocket.chat.login.vo;

/**
 * 注册VO
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
public class RegisterVO {
    private String password;
    private String userName;
    private String userNickname;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
