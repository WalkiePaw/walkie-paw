package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatroomRepositoryOverride {
    Slice<ChatroomListResponse> findByMemberId(Integer memberId, Pageable pageable);

    Page<TransactionResponse> findTransaction(Integer memberId, Pageable pageable);
}
