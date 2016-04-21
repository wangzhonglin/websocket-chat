package websocket.chat.message.vo;

/**
 * WsFriendRequest
 * Date: 2016-04-21
 *
 * @author wangzhonglin
 */
public class WsFriendRequest {
    private int senderUserId;
    private int receiverUserId;

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
