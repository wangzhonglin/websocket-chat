package websocket.chat.websocket.init;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Init childHandler
 * Date: 2016-02-18
 *
 * @author wangzhonglin
 */
@Service
public class ChildHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MessageServiceHandler handler;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline cp = ch.pipeline();
        cp.addLast("decoder", new HttpRequestDecoder())
                .addLast("encoder", new HttpResponseEncoder())
                .addLast("compressor", new HttpContentCompressor())
                .addLast("aggregator", new HttpObjectAggregator(1048576))
                .addLast("handler", handler);
    }
}
