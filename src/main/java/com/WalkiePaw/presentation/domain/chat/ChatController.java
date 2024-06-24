package com.WalkiePaw.presentation.domain.chat;

import com.WalkiePaw.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;

//    @GetMapping
//    public ResponseEntity<ChatMsgListResponse> chatMsgList(@RequestParam Integer chatroomId) {
//        chatService.listChatMsg(chatroomId);
//    }
}
