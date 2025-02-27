package cn.machaojin.websocket;

import cn.machaojin.service.websocket.CreateProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 16:53
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateProjectHandler implements WebSocketHandler {

    private final CreateProjectService createProjectService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("CreateProjectHandler收到新的连接{}",session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("CreateProjectHandler收到消息{}",message);
        try {
            createProjectService.createProject(session,message);
            createProjectService.analyzeProject();
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 5000 * 30)
    public void search(){
        log.info("定时任务开始执行！");
        createProjectService.analyzeProject();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WS 连接发生错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WS 关闭连接");
    }

    // 支持分片消息
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
