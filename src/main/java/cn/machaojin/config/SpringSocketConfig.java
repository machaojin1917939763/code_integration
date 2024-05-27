package cn.machaojin.config;

import cn.machaojin.websocket.CreateProjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Ma Chaojin
 * @since 2024-05-05 16:54
 */
@Configuration
@EnableWebSocket
public class SpringSocketConfig implements WebSocketConfigurer {

    @Autowired
    private CreateProjectHandler projectHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(projectHandler, "/create_project").setAllowedOrigins("*");
    }
}

