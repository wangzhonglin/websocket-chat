package websocket.chat.websocket.init;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import websocket.chat.constant.Constant;
import websocket.chat.message.dispatcher.DispatcherService;
import websocket.chat.util.ApiResponse;
import websocket.chat.util.StringUtil;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

/**
 * MessageServiceHandler
 * Date: 2016-02-22
 *
 * @author wangzhonglin
 */
@Component
@ChannelHandler.Sharable
public class MessageServiceHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private DispatcherService dispatcherSvc;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelUnregistered();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelActive();
    }

    /**
     * Read message from channel
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof HttpRequest) {
                handleHttpRequest(ctx, (HttpRequest) msg);
            } else if (msg instanceof WebSocketFrame) {
                handleWebSocketFrame(ctx, (WebSocketFrame) msg);
            } else {
                handleErrorRequest(ctx);
            }
        } finally {
            ReferenceCountUtil.safeRelease(msg);
        }
    }

    /**
     * Handle the first http request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest request) {
        HttpHeaders headers = request.headers();
        if (!HttpMethod.GET.equals(request.getMethod()) || !"websocket".equalsIgnoreCase(headers.get("Upgrade"))) {
            handleErrorRequest(ctx);
        }

        String webSocketURL = Constant.WS + headers.get(HttpHeaders.Names.HOST);
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(webSocketURL, null, false);
        WebSocketServerHandshaker handShaker = factory.newHandshaker(request);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            WebSocketVersion version = handShaker.version();
            if (version == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                if (Constant.WEB_SOCKET_VERSION.equals(version.name())) {
                    handShaker.handshake(ctx.channel(), request);
                    System.out.println("handle http request");
                } else {
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                }
            }
        }
    }

    /**
     * Handle websocket frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame request) {
        if (request instanceof CloseWebSocketFrame) {
            ctx.close();
        } else if (request instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PingWebSocketFrame());
        } else if (request instanceof TextWebSocketFrame) {
            TextWebSocketFrame textReq = (TextWebSocketFrame) request;
            String text = textReq.text();
            if (!StringUtil.isEmpty(text)) {
                RequestVO requestVO = JSON.parseObject(text, RequestVO.class);
                String result = dealMsg(requestVO, ctx);
                ctx.writeAndFlush(JSON.toJSONString(result));
            }
        }
    }

    private String dealMsg(RequestVO requestVO, ChannelHandlerContext ctx) {
        String result = dispatcherSvc.dispatchChannelTask(requestVO, ctx);
        if (result == null) {
            return JSON.toJSONString(ApiResponse.create(false, "消息处理失败", Constant.ERROR_CODE, null));
        } else {
            return result;
        }
    }

    /**
     * Handle bad request
     */
    private void handleErrorRequest(ChannelHandlerContext ctx) {
        System.out.println("handle error request");
        DefaultHttpResponse resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        ctx.write(resp);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read complete");
//        ctx.fireChannelReadComplete();
        ctx.flush();
    }

    /**
     * Heartbeat mechanism
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("heartbeat");
//        ctx.fireUserEventTriggered(evt);
        ResponseVO responseVO = new ResponseVO();
        String msg = JSON.toJSONString(responseVO);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush(new TextWebSocketFrame(msg));
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new TextWebSocketFrame(msg));
                ctx.close();
            } else if (e.state() == IdleState.ALL_IDLE) {
                ctx.writeAndFlush(new TextWebSocketFrame());
                ctx.close();
            }
        }
    }
}
