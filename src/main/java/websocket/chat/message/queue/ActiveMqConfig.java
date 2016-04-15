package websocket.chat.message.queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;
import websocket.chat.constant.Constant;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * ActiveMqConfig
 * Date: 2016-04-07
 *
 * @author wangzhonglin
 */
@Component
public class ActiveMqConfig {

    public Destination getDestination(){
        return new ActiveMQQueue(Constant.JMS_MESSAGE_TO_USER_DESTINATION);
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainerFactory(ConnectionFactory connectionFactory, JmsMessageListener jmsMessageListener){
        DefaultMessageListenerContainer factory = new DefaultMessageListenerContainer();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestination(getDestination());
        factory.setMessageListener(jmsMessageListener);
        return factory;
    }
}
