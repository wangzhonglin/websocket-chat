package websocket.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * web application
 * Date: 2015-12-28
 *
 * @author wangzhonglin
 */
public class Application extends SpringBootServletInitializer {

    /**
     * gradle bootRun main function
     */
    public static void main(String... args) {
        SpringApplication.run(SiteConfig.class, args);
    }

    /**
     * extend {@link SpringBootServletInitializer SpringBootServletInitializer}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SiteConfig.class);
    }
}
