package com.example.framework.notify;

import com.example.system.domain.Message;
import com.example.system.domain.User;
import com.example.system.mapper.AdminMessageMapper;
import com.example.system.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一通知工具：WebSocket 实时推送 + 站内信持久化
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationHelper {

    private final SimpMessagingTemplate messagingTemplate;
    private final AdminMessageMapper adminMessageMapper;
    private final AdminUserMapper adminUserMapper;

    /**
     * 发送 WebSocket 实时广播通知（所有在线用户收到弹窗）
     */
    public void pushWebSocket(String title, String content) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("title", title);
        payload.put("content", content);
        payload.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        messagingTemplate.convertAndSend("/topic/notification", payload);
    }

    /**
     * 给指定用户发送站内信
     */
    public void sendMessage(Long senderId, Long receiverId, String msgType, String title, String content) {
        try {
            Message msg = new Message();
            msg.setSenderId(senderId);
            msg.setReceiverId(receiverId);
            msg.setMsgType(msgType);
            msg.setTitle(title);
            msg.setContent(content);
            adminMessageMapper.addMessage(msg);
        } catch (Exception e) {
            log.error("站内信发送失败: receiverId={}, title={}", receiverId, title, e);
        }
    }

    /**
     * 给所有后台用户发送站内信
     */
    @Async
    public void sendMessageToAll(Long senderId, String msgType, String title, String content) {
        try {
            List<User> users = adminUserMapper.queryAllUser();
            for (User user : users) {
                sendMessage(senderId, user.getId(), msgType, title, content);
            }
        } catch (Exception e) {
            log.error("批量站内信发送失败: title={}", title, e);
        }
    }

    /**
     * 广播通知 = WebSocket实时弹窗 + 站内信持久化给所有用户
     */
    @Async
    public void broadcast(Long senderId, String title, String content) {
        pushWebSocket(title, content);
        sendMessageToAll(senderId, "notice", title, content);
    }

    /**
     * 给超级管理员(ID=1)发送系统预警
     */
    public void warnAdmin(String title, String content) {
        sendMessage(0L, 1L, "warn", title, content);
    }
}
