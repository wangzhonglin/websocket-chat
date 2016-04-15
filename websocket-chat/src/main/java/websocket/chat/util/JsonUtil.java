package websocket.chat.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JsonUtil
 * Date: 2016-03-21
 *
 * @author wangzhonglin
 */
public class JsonUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> T safelyParseObject(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            LOG.error("Error when parse text to object, text={}", text, e);
        }
        return null;
    }
}
