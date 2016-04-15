package websocket.chat.message.queue;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Message queue, save message.
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageQueue<T> {
    private static final int capacity = 10000;
    private BlockingQueue<T> queue =  new LinkedBlockingQueue<>(capacity);

    public boolean add(T message) throws InterruptedException {
        return queue.add(message);
    }

    public T get() throws InterruptedException {
        return queue.take();
    }
}
