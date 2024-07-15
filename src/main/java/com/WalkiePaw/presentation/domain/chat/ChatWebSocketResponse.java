package com.WalkiePaw.presentation.domain.chat;

import lombok.Data;

@Data
public class ChatWebSocketResponse {
    private final Integer writerId;
    private final String nickname;
    private final String content;
    private final String sentTime;
}
