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

    @Override
    public String toString() {
        return "RequestVO{" +
                "method='" + method + '\'' +
                ", request='" + request + '\'' +
                '}';
    }
}
