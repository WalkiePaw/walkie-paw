package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chats")
public class ChatController {
    public static final String CHAT_URI = "/chats/";
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatMsgListResponse>> getChatListByChatroomId(final @RequestParam Integer chatroomId) {
        List<ChatMsgListResponse> chats = chatService.findChatsByChatroomId(chatroomId);
        return ResponseEntity.ok(chats);
    }

    @MessageMapping
    public ResponseEntity<Void> addChat(final @Payload ChatAddRequest request) {
        Integer id = chatService.saveChatMsg(request);
        return ResponseEntity.created(URI.create(CHAT_URI + id)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> chatMsgUpdateIsRead(final @PathVariable("id") Integer chatroomId) {
        chatService.bulkUpdateIsRead(chatroomId);
        return ResponseEntity.noContent().build();
    }

}
