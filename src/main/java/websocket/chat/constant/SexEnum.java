package websocket.chat.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * SexEnum
 * Date: 2016-04-20
 *
 * @author wangzhonglin
 */
public enum SexEnum {
    FEMALE((byte) 0, "女"),
    MALE((byte) 1, "男");

    public final byte value;
    public final String description;
    private static Map<Byte, SexEnum> map = createMap();

    SexEnum(final byte value, final String description) {
        this.value = value;
        this.description = description;
    }

    private static Map<Byte, SexEnum> createMap() {
        SexEnum[] array = SexEnum.values();
        Map<Byte, SexEnum> map = new HashMap<>(array.length);
        for (SexEnum e : array) {
            map.put(e.value, e);
        }
        return map;
    }

    public static SexEnum byte2Enum(final byte value) {
        return map.get(value);
    }
}
