package com.WalkiePaw.presentation.domain.board.dto;
import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.PriceType;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class BoardAddRequest {

    private final Integer memberId;
    private final String title;
    private final String content;
    private final int price;
    private final BoardCategory category;
    private final PriceType priceType;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String location;
    private final String detailedLocation;
    private final boolean priceProposal;

    /**
     * BoardAddResponse -> Board 객체로 만드는 toEntity 메서드 필요
     */
    public static Board toEntity(final BoardAddRequest request, final Member member) {

        return Board.builder()
                .member(member)
                .content(request.content)
                .title(request.title)
                .price(request.price)
                .category(request.category)
                .startTime(request.startTime)
                .endTime(request.endTime)
                .priceType(request.priceType)
                .location(request.location)
                .detailedLocation(request.detailedLocation)
                .priceProposal(request.priceProposal)
                .build();
    }
}
