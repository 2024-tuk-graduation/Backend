package com.example.tukgraduation.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url => /ws
        registry.addEndpoint("/ws") // 연결될 엔드포인트
                .setAllowedOriginPatterns("*");

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // stomp 최대 버퍼 사이즈를 늘리기 위한 설정
        registry.setMessageSizeLimit(50000 * 1024);
        registry.setSendBufferSizeLimit(10240 * 1024);
        registry.setSendTimeLimit(600000); //10min
    }
}