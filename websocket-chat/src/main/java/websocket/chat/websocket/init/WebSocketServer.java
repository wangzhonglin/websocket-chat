package websocket.chat.websocket.init;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Server entrance
 * Date: 2016-02-18
 *
 * @author wangzhonglin
 */
@Service
public class WebSocketServer {
    private static Logger LOG  = LoggerFactory.getLogger(WebSocketServer.class);

    static ServerBootstrap bootstrap;
    static EventLoopGroup bossGroup;
    static EventLoopGroup workGroup;

    @Value("${nettyServerConfig.serverIp}")
    private String serverIp;

    @Value("${nettyServerConfig.serverPort}")
    private int serverPort;

    @Value("${nettyServerConfig.bossThread}")
    private int bossThread;

    @Value("${nettyServerConfig.workThread}")
    private int workThread;

    @Autowired
    private ChildHandlerInitializer childHandlerInitializer;

    public void start() {
        try {
            bossGroup = new NioEventLoopGroup(bossThread);
            workGroup = new NioEventLoopGroup(workThread);
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(childHandlerInitializer);
            ChannelFuture f = bootstrap.bind(serverPort).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
