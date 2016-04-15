package websocket.chat.message.route;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.SessionStatusEnum;
import websocket.chat.message.queue.ActiveMqConfig;
import websocket.chat.message.queue.JmsMessageSender;
import websocket.chat.message.service.MessageService;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.session.vo.SessionVO;

/**
 * RouteService
 * Date: 2016-04-07
 *
 * @author wangzhonglin
 */
@Service
public class RouteService {
    private static final Logger LOG = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private JmsMessageSender jmsMessageSender;
    @Autowired
    private ActiveMqConfig activeMqConfig;

    public void handleMessage(MessageVO messageVO, SessionVO sessionVO) {
        if (sessionVO.getStatus() == SessionStatusEnum.SUCCESS.value) {
            sendMessage(messageVO);
        }
    }

    private void sendMessage(MessageVO messageVO) {
        try {
            messageService.createMessage(messageVO);
            jmsMessageSender.sendTextMessage(activeMqConfig.getDestination(), JSON.toJSONString(messageVO));
        } catch (Exception e) {
            LOG.error("SendMessage failed in RouteService. messageVO={}", messageVO, e);
        }
    }
}
