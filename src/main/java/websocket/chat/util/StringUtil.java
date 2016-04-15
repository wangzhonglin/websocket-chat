package websocket.chat.util;

/**
 * String util
 * Date: 2016-02-23
 *
 * @author wangzhonglin
 */
public class StringUtil {

    /**
     * Check string is empty or not
     */
    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static boolean allDigit(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int str2int(String str, int dftValue) {
        if (isEmpty(str)) {
            return dftValue;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return dftValue;
        }
    }

}
