package websocket.chat.message.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import websocket.chat.message.service.PushWebsocketMessage;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.util.JsonUtil;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * JmsMessageListener
 * Date: 2016-04-07
 *
 * @author wangzhonglin
 */
@Component
public class JmsMessageListener implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(JmsMessageListener.class);

    @Autowired
    private PushWebsocketMessage pushWebsocketMessage;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            LOG.warn("not text message, bad user message type.",message);
            return ;
        }

        try {
            String jsonText = ((TextMessage) message).getText();
            MessageVO messageVO = JsonUtil.safelyParseObject(jsonText, MessageVO.class);
            if (messageVO == null) {
                LOG.warn("bad json message {}.",jsonText);
                return;
            }
            pushWebsocketMessage.pushMessage2User(messageVO);
        }catch (JMSException e){
            LOG.error("get user jms message failed.",e);
        }catch (Exception e){
            LOG.error("user message failed.",e);
        }
    }
}
