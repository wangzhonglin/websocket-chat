package websocket.chat.user.vo;

/**
 * 添加好友 Request
 * Date: 2016-04-03
 *
 * @author wangzhonglin
 */
public class AddFriendRequest {
    private String loginSessionId;
    private int senderUserId;
    private int receiverUserId;

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
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
