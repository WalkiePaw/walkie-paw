package com.WalkiePaw.global.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        session.sendMessage(new TextMessage("message: " + message.getPayload()));
    }
}
