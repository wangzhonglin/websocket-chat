package websocket.chat.message.vo;

import java.util.List;

/**
 * HistoryMessageListResponse
 * Date: 2016-04-15
 *
 * @author wangzhonglin
 */
public class HistoryMessageListResponse {
    private List<EachHistoryMessage> historyMessageList;
    private int lastMessageId;

    public List<EachHistoryMessage> getHistoryMessageList() {
        return historyMessageList;
    }

    public void setHistoryMessageList(List<EachHistoryMessage> historyMessageList) {
        this.historyMessageList = historyMessageList;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public static class EachHistoryMessage {
        private int userId;
        private String userNickname;
        private String content;
        private int messageType;
        private long messageTime;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public long getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(long messageTime) {
            this.messageTime = messageTime;
        }
    }
}
