package websocket.chat.websocket.vo;

/**
 * RequestVO
 * Date: 2016-02-23
 *
 * @author wangzhonglin
 */
public class RequestVO {
    private String method;
    private String request;
    /**
     * websocket_chat_login中的sessionId
     */
    private String loginSessionId;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    @Override
    public String toString() {
        return "RequestVO{" +
                "method='" + method + '\'' +
                ", request='" + request + '\'' +
                ", loginSessionId='" + loginSessionId + '\'' +
                '}';
    }
}
