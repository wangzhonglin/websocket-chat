package websocket.chat.message.vo;

/**
 * HistoryMessageListRequest
 * Date: 2016-04-15
 *
 * @author wangzhonglin
 */
public class HistoryMessageListRequest {
    private String loginSessionId;
    private int userId;
    private int sessionId;
    private int lastMessageId;
    private int page;
    private int limit;
    private int existMsgNum;

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

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int messageId) {
        this.lastMessageId = messageId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getExistMsgNum() {
        return existMsgNum;
    }

    public void setExistMsgNum(int existMsgNum) {
        this.existMsgNum = existMsgNum;
    }
}
