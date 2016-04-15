package websocket.chat.websocket.manage;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.UserStatusEnum;
import websocket.chat.login.service.LoginService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WebsocketChannelManager
 * Date: 2016-03-07
 *
 * @author wangzhonglin
 */
@Service
public class WebsocketChannelManager {
    private static final Logger LOG = LoggerFactory.getLogger(WebsocketChannelManager.class);
    private Map<Integer, ChannelHandlerContext> userChannelMap = new HashMap<>();
    private Map<ChannelHandlerContext, Integer> channelUserMap = new HashMap<>();
    private Lock lock = new ReentrantLock();

    @Autowired
    private LoginService loginService;

    /**
     * Socket create
     *
     * @param userId        user id
     * @param ctx           ChannelHandlerContext
     */
    public void socketCreated(Integer userId, ChannelHandlerContext ctx) {
        try {
            lock.lock();
            ChannelHandlerContext oldChannel = userChannelMap.get(userId);
            if (oldChannel != null) {
                userChannelMap.remove(userId);
                channelUserMap.remove(oldChannel);
                oldChannel.close();
            }
            userChannelMap.put(userId, ctx);
            channelUserMap.put(ctx, userId);
        } catch (Exception e) {
            LOG.error("Error when socketCreated, cannot finish user and channel map with userId={}", userId, e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Socket closed
     *
     * @param ctx           ChannelHandlerContext
     */
    public void socketClosed(ChannelHandlerContext ctx) {
        try {
            lock.lock();
            Integer userId = channelUserMap.remove(ctx);
            if (userId != null) {
                userChannelMap.remove(userId);
                loginService.updateUserStatus(userId, UserStatusEnum.OFFLINE);
            }
        } catch (Exception e) {
            LOG.error("delete socket session map error.", e);
        } finally {
            lock.unlock();
            ctx.close();
        }
    }

    public ChannelHandlerContext getChannel(int userId) {
        return userChannelMap.get(userId);
    }
}
