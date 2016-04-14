package websocket.chat.message.dispatcher;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.message.handler.MessageBaseService;
import websocket.chat.message.handler.MessageConnectService;
import websocket.chat.message.handler.MessageDisconnectService;
import websocket.chat.message.handler.MessageSendService;
import websocket.chat.util.ApiResponse;
import websocket.chat.websocket.vo.RequestVO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dispatch to precise message service
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Service
public class DispatcherService implements ApplicationListener<ApplicationEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(DispatcherService.class);
    private static final Map<String, MessageBaseService> map = new ConcurrentHashMap<>();

    @Autowired
    private MessageConnectService msgConnectSvc;
    @Autowired
    private MessageDisconnectService msgDisconnectSvc;
    @Autowired
    private MessageSendService msgSendSvc;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        map.put(Constant.METHOD_LOGIN, msgConnectSvc);
        map.put(Constant.METHOD_LOGOUT, msgDisconnectSvc);
        map.put(Constant.METHOD_SEND, msgSendSvc);
    }

    private MessageBaseService register(RequestVO requestVO) {
        return map.get(requestVO.getMethod());
    }

    public String dispatchChannelTask(RequestVO requestVO, ChannelHandlerContext ctx) {
        MessageBaseService msgBaseSvc = register(requestVO);
        if (msgBaseSvc == null) {
            LOG.info("No match for method={}, dispatch task failed.", requestVO.getMethod());
            return JSON.toJSONString(ApiResponse.create(false, "请求方法不正确", Constant.ERROR_CODE, null));
        }
        return msgBaseSvc.executeChannel(requestVO, ctx);
    }
}
