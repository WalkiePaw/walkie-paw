package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatroomRepositoryOverride {
    Slice<ChatroomListResponse> findByMemberId(Integer memberId, Pageable pageable);

    Page<TransactionResponse> findTransaction(Integer memberId, Pageable pageable);
}
