package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chats")
public class ChatController {
    public static final String CHAT_URI = "/chats/";
    private final ChatService chatService;
    private final SimpMessagingTemplate webSocket;

    @GetMapping
    public ResponseEntity<List<ChatMsgListResponse>> getChatListByChatroomId(final @RequestParam Integer chatroomId) {
        List<ChatMsgListResponse> chats = chatService.findChatsByChatroomId(chatroomId);
        return ResponseEntity.ok(chats);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> chatMsgUpdateIsRead(final @PathVariable("id") Integer chatroomId) {
        chatService.bulkUpdateIsRead(chatroomId);
        return ResponseEntity.noContent().build();
    }

}
