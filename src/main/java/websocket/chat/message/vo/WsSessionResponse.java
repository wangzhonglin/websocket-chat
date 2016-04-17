package websocket.chat.message.vo;

/**
 * WsSessionResponse
 * Date: 2016-04-17
 *
 * @author wangzhonglin
 */
public class WsSessionResponse {
    private int sessionId;
    private int friendId;
    private String friendNickname;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }
}
