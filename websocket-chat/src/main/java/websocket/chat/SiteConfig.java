package websocket.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import websocket.chat.database.DatabaseConfig;
import websocket.chat.websocket.init.WebSocketServer;

/**
 * Configure
 * Date: 2015-12-28
 *
 * @author wangzhonglin
 */
@SpringBootApplication
@Import(DatabaseConfig.class)
@EnableAutoConfiguration
public class SiteConfig implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SiteConfig.class);

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            try {
                webSocketServer.start();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }).start();
    }
}
