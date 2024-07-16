package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
import com.WalkiePaw.presentation.domain.chat.dto.ChatWebSocketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/chats/{chatroomId}")
    public void addChat(@DestinationVariable("chatroomId") Integer chatroomId, @Payload ChatAddRequest request) {
        String destination = "/chats/" + chatroomId;
        ChatWebSocketResponse response = new ChatWebSocketResponse(request.getWriterId(), request.getNickname(), request.getContent(), request.getSentTime());
        simpMessagingTemplate.convertAndSend(destination, response);
        chatService.saveChatMsg(chatroomId, request);
    }
}
