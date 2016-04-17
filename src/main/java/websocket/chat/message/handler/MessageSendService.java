package websocket.chat.message.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import websocket.chat.constant.Constant;
import websocket.chat.constant.DeleteFlagEnum;
import websocket.chat.constant.MessageStatusEnum;
import websocket.chat.constant.SessionStatusEnum;
import websocket.chat.login.service.LoginService;
import websocket.chat.message.route.RouteService;
import websocket.chat.message.vo.MessageVO;
import websocket.chat.message.vo.WsMessageRequest;
import websocket.chat.session.service.SessionService;
import websocket.chat.session.vo.SessionVO;
import websocket.chat.util.JsonUtil;
import websocket.chat.websocket.vo.RequestVO;
import websocket.chat.websocket.vo.ResponseVO;

import java.util.Date;

/**
 * MessageSendService
 * Date: 2016-03-03
 *
 * @author wangzhonglin
 */
@Service
public class MessageSendService extends MessageBaseService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSendService.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private RouteService routeService;

    @Override
    public String executeChannel(RequestVO requestVO, ChannelHandlerContext ctx) {
        WsMessageRequest wsMessageRequest = JsonUtil.safelyParseObject(requestVO.getRequest(), WsMessageRequest.class);
        SessionVO sessionVO = createSessionIfNecessary(wsMessageRequest);
        if (!userAbleToSendMessage(requestVO.getLoginSessionId(), wsMessageRequest, sessionVO)) {
            return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_ERROR, false, "发送失败, 消息检验不成功"));
        }
        routeService.handleMessage(buildMessage(wsMessageRequest, sessionVO), sessionVO);
        return JSON.toJSONString(ResponseVO.create(Constant.METHOD_PUSH_SUCCESS, true, "发送成功"));
    }

    private boolean userAbleToSendMessage(String loginSessionId, WsMessageRequest wsMessageRequest, SessionVO sessionVO) {
        if (wsMessageRequest == null || sessionVO == null) {
            LOG.info("Empty weMessageRequest.");
            return false;
        }

        if (!loginService.checkLogin(loginSessionId, wsMessageRequest.getSenderUserId())) {
            LOG.info("验证 sessionId={}, userId={} 失败", loginSessionId, wsMessageRequest.getSenderUserId());
            return false;
        }

        if (sessionVO.getStatus() == SessionStatusEnum.CLOSED.value) {
            LOG.info("Session is already closed, bad try for sending message. sessionId={}, wsMessageRequest={}", loginSessionId, wsMessageRequest);
            return false;
        }
        return true;
    }

    private SessionVO createSessionIfNecessary(WsMessageRequest wsMessageRequest) {
        try {
            if (wsMessageRequest == null) {
                LOG.info("Empty weMessageRequest.");
                return null;
            }
            SessionVO sessionVO = sessionService.getSessionBySessionId(wsMessageRequest.getSessionId());
            if (sessionVO == null) {
                sessionVO = new SessionVO();
                sessionVO.setSenderUserId(wsMessageRequest.getSenderUserId());
                sessionVO.setReceiverUserId(wsMessageRequest.getReceiverUserId());
                sessionVO.setStatus(SessionStatusEnum.SUCCESS.value);
                Date currDate = new Date();
                sessionVO.setCreateTime(currDate);
                sessionVO.setUpdateTime(currDate);
                sessionVO.setDelFlag(DeleteFlagEnum.OK.value);
                sessionService.createSession(sessionVO);
            }
            return sessionVO;
        } catch (Exception e) {
            LOG.error("CreateSessionIfNecessary failed. wsMessageRequest={}", wsMessageRequest, e);
            return null;
        }
    }

    private MessageVO buildMessage(WsMessageRequest wsMessageRequest, SessionVO sessionVO) {
        MessageVO messageVO = new MessageVO();
        messageVO.setSessionId(sessionVO.getId());
        messageVO.setSenderUserId(wsMessageRequest.getSenderUserId());
        messageVO.setReceiverUserId(wsMessageRequest.getReceiverUserId());
        messageVO.setMessageType(wsMessageRequest.getMessageType());
        messageVO.setContent(wsMessageRequest.getContent());
        messageVO.setStatus(MessageStatusEnum.UNREAD.value);
        Date currDate = new Date();
        messageVO.setCreateTime(currDate);
        messageVO.setUpdateTime(currDate);
        messageVO.setDelFlag(DeleteFlagEnum.OK.value);
        return messageVO;
    }
}
