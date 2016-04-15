package websocket.chat.constant;

/**
 * Constant
 * Date: 2016-02-23
 *
 * @author wangzhonglin
 */
public class Constant {

    /**
     * websocket protocol
     */
    public static final String WS = "ws://";

    /**
     * websocket version
     */
    public static final String WEB_SOCKET_VERSION = "V13";

    /**
     * websocket method
     */
    public static final String METHOD_SEND = "method_send";
    public static final String METHOD_LOGIN = "method_login";
    public static final String METHOD_LOGOUT = "method_logout";
    public static final String METHOD_PUSH = "method_push";

    /**
     * JSON_CALLBACK
     */
    public static final String JSON_CALLBACK = "json_callback";

    /**
     * error code
     */
    public static int SUCCESS_CODE = 10000;
    public static int ERROR_CODE = 10001;
    public static int METHOD_UNKNOWN_CODE = 10002;

    /**
     * SessionId length
     */
    public static int SESSION_ID_LENGTH = 20;

    /**
     * ActiveMQ destination
     */
    public static String JMS_MESSAGE_TO_USER_DESTINATION = "jms_message_to_user_destination";
}
