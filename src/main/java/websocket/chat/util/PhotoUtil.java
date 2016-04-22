package websocket.chat.util;

import org.springframework.util.Base64Utils;
import websocket.chat.constant.Constant;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * PhotoUtil
 * Date: 2016-04-22
 *
 * @author wangzhonglin
 */
public class PhotoUtil {

    public static String generatePhoto(String base64Photo, int userId) {
        try {
            if (base64Photo.equals(Constant.DEFAULT_IMG_IN_BASE64)) {
                return Constant.DEFAULT_USER_AVATAR;
            }
            String imgName = Constant.DEFAULT_IMG_NAME_PREFIX + userId;
            byte[] imgNameInBytes = imgName.getBytes();
            String imgNameInBase64 = Base64Utils.encodeToString(imgNameInBytes) + ".png";
            byte[] imgData = Base64Utils.decodeFromString(base64Photo);
            String imgFilePath = Constant.DEFAULT_IMG_PATH + imgNameInBase64;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(imgData);
            out.flush();
            out.close();
            return Constant.DEFAULT_IMG_PATH_PREFIX_FOR_SRC + imgNameInBase64;
        } catch (Exception e) {
            return Constant.DEFAULT_USER_AVATAR;
        }
    }
}
