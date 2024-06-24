package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.PriceType;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardAddRequest {

    private final Integer memberId;
    private final String title;
    private final String content;
    private final int price;
    private final LocalDateTime meetingTime;
    private final PriceType priceType;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * BoardAddResponse -> Board 객체로 만드는 toEntity 메서드 필요
     */
    public static Board toEntity(final BoardAddRequest request, final Member member) {

        return Board.builder()
                .member(member)
                .content(request.getContent())
                .title(request.getTitle())
                .price(request.getPrice())
                .meetingTime(request.getMeetingTime())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .priceType(request.getPriceType())
                .build();
    }
}
