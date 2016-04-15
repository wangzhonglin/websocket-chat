package websocket.chat.message.vo;

/**
 * Websocket消息请求
 * Date: 2016-04-06
 *
 * @author wangzhonglin
 */
public class WsMessageRequest {
    private int sessionId;
    private int senderUserId;
    private int receiverUserId;
    private int messageType;
    private String content;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
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

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WsMessageRequest{" +
                "sessionId=" + sessionId +
                ", senderUserId=" + senderUserId +
                ", receiverUserId=" + receiverUserId +
                ", messageType=" + messageType +
                ", content='" + content + '\'' +
                '}';
    }
}
