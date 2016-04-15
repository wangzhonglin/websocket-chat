package websocket.chat.websocket.vo;

/**
 * ResponseVO
 * Date: 2016-02-23
 *
 * @author wangzhonglin
 */
public class ResponseVO<T> {
    private String method;
    private boolean success;
    private T data;

    public static <T> ResponseVO<T> create(String method, boolean success, T data) {
        ResponseVO<T> rsp = new ResponseVO<>();
        rsp.setMethod(method);
        rsp.setSuccess(success);
        rsp.setData(data);
        return rsp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
