package websocket.chat.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * UserStatusEnum
 * Date: 2016-03-21
 *
 * @author wangzhonglin
 */
public enum UserStatusEnum {
    ONLINE((byte) 0, "在线"),
    BUSY((byte) 1, "忙碌"),
    OFFLINE((byte) 2, "离线");

    public final byte value;
    public final String description;
    private static Map<Byte, UserStatusEnum> map = createMap();

    UserStatusEnum(final byte value, final String description) {
        this.value = value;
        this.description = description;
    }

    private static Map<Byte, UserStatusEnum> createMap() {
        UserStatusEnum[] array = UserStatusEnum.values();
        Map<Byte, UserStatusEnum> map = new HashMap<>(array.length);
        for (UserStatusEnum e : array) {
            map.put(e.value, e);
        }
        return map;
    }

    public static UserStatusEnum byte2Enum(final byte value) {
        return map.get(value);
    }
}
