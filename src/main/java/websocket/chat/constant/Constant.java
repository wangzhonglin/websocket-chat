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
    public static final String METHOD_PUSH_ERROR = "method_push_error";
    public static final String METHOD_PUSH_SUCCESS = "method_push_success";
    public static final String METHOD_CREATE_SESSION = "method_create_session";
    public static final String METHOD_DELETE_FRIEND = "method_delete_friend";
    public static final String METHOD_ADD_FRIEND = "method_add_friend";

    /**
     * JSON_CALLBACK
     */
    public static final String JSON_CALLBACK = "json_callback";

    /**
     * error code
     */
    public static final int SUCCESS_CODE = 10000;
    public static final int ERROR_CODE = 10001;

    /**
     * System message
     */
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String NULL_PARAM_MESSAGE = "Empty params";
    public static final String USER_NOT_LOGIN_MESSAGE = "User has not login";
    public static final String EXECUTION_FAILURE = "Execution failure";
    public static final String UNKNOWN_METHOD = "Method is unknown";

    /**
     * SessionId length
     */
    public static final int SESSION_ID_LENGTH = 20;

    /**
     * ActiveMQ destination
     */
    public static final String JMS_MESSAGE_TO_USER_DESTINATION = "jms_message_to_user_destination";

    /**
     * Default user information
     */
    public static final String DEFAULT_USER_SIGNATURE = "";
    public static final String DEFAULT_USER_AVATAR = "images/demo/av3.jpg";
    public static final String DEFAULT_IMG_IN_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAALQAAAC0CAYAAAA9zQYyAAADWUlEQVR4Xu3SAQ0AAAjDMO7fND6W4uCjO6dAqMBCW0xR4ICGIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO40BmoFUAaBT7zQGaAZSBYBOvdMYoBlIFQA69U5jgGYgVQDo1DuNAZqBVAGgU+80BmgGUgWATr3TGKAZSBUAOvVOY4BmIFUA6NQ7jQGagVQBoFPvNAZoBlIFgE690xigGUgVADr1TmOAZiBVAOjUO415PNEAtQUOHXEAAAAASUVORK5CYII=";
    public static final String DEFAULT_IMG_PATH = "d://project//websocket-chat//src//main//webapp//images//demo//";
    public static final String DEFAULT_IMG_NAME_PREFIX = "websocketuserid-";
    public static final String DEFAULT_IMG_PATH_PREFIX_FOR_SRC = "images/demo/";
}
