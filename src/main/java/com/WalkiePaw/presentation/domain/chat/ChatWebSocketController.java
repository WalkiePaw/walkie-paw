package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/api/v1/ws/chats")
    @SendTo("/chats")
    public ChatMsgListResponse addChat(@Payload ChatAddRequest request) {
        System.out.println("request = " + request);
        return chatService.saveChatMsg(request);
    }
}
