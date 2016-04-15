package websocket.chat.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除标志: 0 正常 1 删除
 * Date: 2016-04-02
 *
 * @author wangzhonglin
 */
public enum DeleteFlagEnum {
    OK((byte) 0, "正常"),
    DELETED((byte) 1, "删除");

    public final byte value;
    public final String description;
    private static Map<Byte, DeleteFlagEnum> map = createMap();

    DeleteFlagEnum(final byte value, final String description) {
        this.value = value;
        this.description = description;
    }

    private static Map<Byte, DeleteFlagEnum> createMap() {
        DeleteFlagEnum[] array = DeleteFlagEnum.values();
        Map<Byte, DeleteFlagEnum> map = new HashMap<>(array.length);
        for (DeleteFlagEnum e : array) {
            map.put(e.value, e);
        }
        return map;
    }

    public static DeleteFlagEnum byte2Enum(final byte value) {
        return map.get(value);
    }
}
