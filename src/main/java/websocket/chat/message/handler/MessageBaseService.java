package websocket.chat.message.handler;

import io.netty.channel.ChannelHandlerContext;
import websocket.chat.websocket.vo.RequestVO;

/**
 * Basic message service interface
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
public abstract class MessageBaseService {

    /**
     * Execute processing message
     */
    public abstract String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx);
}
