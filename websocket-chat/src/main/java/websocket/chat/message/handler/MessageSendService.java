package websocket.chat.message.handler;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;
import websocket.chat.websocket.vo.RequestVO;

/**
 * MessageSendService
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Service
public class MessageSendService extends MessageBaseService {
    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx){
        return "";
    }
}
