package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/chats/{chatroomId}")
    public ChatMsgListResponse addChat(@DestinationVariable("chatroomId") Integer chatroomId, @Payload ChatAddRequest request) {
        return chatService.saveChatMsg(chatroomId, request);
    }
}
