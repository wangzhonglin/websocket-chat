package websocket.chat.message.vo;

/**
 * WsSessionRequest
 * Date: 2016-04-17
 *
 * @author wangzhonglin
 */
public class WsSessionRequest {
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
