package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;

import java.util.List;

public interface ChatroomRepositoryOverride {
    List<ChatroomListResponse> findByMemberId(Integer memberId);
}
