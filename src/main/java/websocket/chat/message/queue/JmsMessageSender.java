package websocket.chat.message.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jmx.JmxException;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * Jms 消息发送
 * Date: 2016-04-07
 *
 * @author wangzhonglin
 */
@Component
public class JmsMessageSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendTextMessage(Destination destination, final String data) throws JmxException {
        jmsTemplate.send(destination, session -> {
            return session.createTextMessage(data);
        });
    }
}
