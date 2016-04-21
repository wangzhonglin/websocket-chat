package websocket.chat.message.vo;

/**
 * WsFriendResponse
 * Date: 2016-04-21
 *
 * @author wangzhonglin
 */
public class WsFriendResponse {
    private int friendId;
    private String friendNickname;
    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
