package websocket.chat.session.vo;

import java.util.List;

/**
 * LatestSessionListResponse
 * Date: 2016-04-11
 *
 * @author wangzhonglin
 */
public class LatestSessionListResponse {
    private List<EachSession> latestSessionList;

    public List<EachSession> getLatestSessionList() {
        return latestSessionList;
    }

    public void setLatestSessionList(List<EachSession> latestSessionList) {
        this.latestSessionList = latestSessionList;
    }

    public static class EachSession {
        private int sessionId;
        private int friendId;
        private String friendNickname;
        private String lastMessageContent;
        private long lastMessageTime;
        private int unreadMsgCount;

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

        public String getLastMessageContent() {
            return lastMessageContent;
        }

        public void setLastMessageContent(String lastMessageContent) {
            this.lastMessageContent = lastMessageContent;
        }

        public long getLastMessageTime() {
            return lastMessageTime;
        }

        public void setLastMessageTime(long lastMessageTime) {
            this.lastMessageTime = lastMessageTime;
        }

        public int getUnreadMsgCount() {
            return unreadMsgCount;
        }

        public void setUnreadMsgCount(int unreadMsgCount) {
            this.unreadMsgCount = unreadMsgCount;
        }
    }
}
