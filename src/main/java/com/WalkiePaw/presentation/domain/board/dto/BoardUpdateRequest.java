package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.PriceType;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardUpdateRequest {

    private final String title;
    private final String content;
    private final int price;
    private final LocalDateTime meetingTime;
    private final PriceType priceType;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

}
