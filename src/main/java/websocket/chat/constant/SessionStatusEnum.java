package websocket.chat.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * SessionStatusEnum
 * Date: 2016-04-06
 *
 * @author wangzhonglin
 */
public enum SessionStatusEnum {
    WAITING((byte) 0, "等待"),
    SUCCESS((byte) 1, "成功"),
    FAILED((byte) 2, "失败"),
    CLOSED((byte) 3, "关闭");

    public final byte value;
    public final String description;
    private static Map<Byte, SessionStatusEnum> map = createMap();

    SessionStatusEnum(final byte value, final String description) {
        this.value = value;
        this.description = description;
    }

    private static Map<Byte, SessionStatusEnum> createMap() {
        SessionStatusEnum[] array = SessionStatusEnum.values();
        Map<Byte, SessionStatusEnum> map = new HashMap<>(array.length);
        for (SessionStatusEnum e : array) {
            map.put(e.value, e);
        }
        return map;
    }

    public static SessionStatusEnum byte2Enum(final byte value) {
        return map.get(value);
    }
}
