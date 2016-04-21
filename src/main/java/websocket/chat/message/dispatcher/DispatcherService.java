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
import websocket.chat.message.handler.*;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

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
    @Autowired
    private MessageSessionService msgSessionSvc;
    @Autowired
    private MessageFriendService msgFriendSvc;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        map.put(Constant.METHOD_LOGIN, msgConnectSvc);
        map.put(Constant.METHOD_LOGOUT, msgDisconnectSvc);
        map.put(Constant.METHOD_SEND, msgSendSvc);
        map.put(Constant.METHOD_CREATE_SESSION, msgSessionSvc);
        map.put(Constant.METHOD_DELETE_FRIEND, msgFriendSvc);
        map.put(Constant.METHOD_ADD_FRIEND, msgFriendSvc);
    }

    private MessageBaseService register(RequestVO requestVO) {
        return map.get(requestVO.getMethod());
    }

    public String dispatchChannelTask(RequestVO requestVO, ChannelHandlerContext ctx) {
        if (requestVO == null) {
            LOG.info("RequestVO is null, cannot dispatchChannelTask.");
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.NULL_PARAM_MESSAGE));
        }
        MessageBaseService msgBaseSvc = register(requestVO);
        if (msgBaseSvc == null) {
            LOG.info("No match for method={}, dispatch task failed.", requestVO.getMethod());
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, Constant.UNKNOWN_METHOD));
        }
        return msgBaseSvc.executeChannel(requestVO, ctx);
    }
}
