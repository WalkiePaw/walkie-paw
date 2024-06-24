package com.WalkiePaw.presentation.domain.chatroom;

import com.WalkiePaw.domain.chatroom.service.ChatroomService;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomAddRequest;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomRespnose;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chatrooms")
public class ChatroomController {
    private final ChatroomService chatroomService;
    private static final String CHATROOM_URI = "/chatrooms/";

///api/v1/{domain}-> get : 목록
///api/v1/{domain} -> post : 등록
///api/v1/{domain}/{id} -> get : 조회
///api/v1/{domain}/{id} -> patch  : 수정
///api/v1/{domain}/{id} -> delete : 삭제

    @GetMapping
    public ResponseEntity<List<ChatroomListResponse>> getChatroomList() {
        List<ChatroomListResponse> chatrooms = chatroomService.findAll();
        return ResponseEntity.ok(chatrooms);
    }

    @PostMapping
    public ResponseEntity<Void> addChatroom(final ChatroomAddRequest request) {
        Integer id = chatroomService.saveChatroom(request);
        return ResponseEntity.created(URI.create(CHATROOM_URI + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatroomRespnose> getChatroom(final @PathVariable Integer id) {
        ChatroomRespnose chatroomById = chatroomService.findChatroomById(id);
        return ResponseEntity.ok(chatroomById);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateChatroom(final @PathVariable Integer id, final ChatroomUpdateRequest request) {
        chatroomService.updateChatroom(id, request);
        return ResponseEntity.noContent().build();
    }
}
