package websocket.chat.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息状态
 * Date: 2016-04-07
 *
 * @author wangzhonglin
 */
public enum MessageStatusEnum {
    UNREAD((byte) 0, "未读"),
    READ((byte) 1, "已读");

    public final byte value;
    public final String description;
    private static Map<Byte, MessageStatusEnum> map = createMap();

    MessageStatusEnum(final byte value, final String description) {
        this.value = value;
        this.description = description;
    }

    private static Map<Byte, MessageStatusEnum> createMap() {
        MessageStatusEnum[] array = MessageStatusEnum.values();
        Map<Byte, MessageStatusEnum> map = new HashMap<>(array.length);
        for (MessageStatusEnum e : array) {
            map.put(e.value, e);
        }
        return map;
    }

    public static MessageStatusEnum byte2Enum(final byte value) {
        return map.get(value);
    }
}
