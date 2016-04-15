package websocket.chat.user.vo;

/**
 * SearchUserRequest
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class SearchUserRequest {
    private String loginSessionId;
    private int userId;
    private String keyword;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
