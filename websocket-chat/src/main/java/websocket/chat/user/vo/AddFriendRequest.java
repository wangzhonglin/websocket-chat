package websocket.chat.user.vo;

/**
 * 添加好友 Request
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
public class AddFriendRequest {
    private String sessionId;
    private int senderUserId;
    private int receiverUserId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }
}
