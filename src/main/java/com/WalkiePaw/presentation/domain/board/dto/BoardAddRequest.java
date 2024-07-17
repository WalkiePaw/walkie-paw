package com.WalkiePaw.presentation.domain.board.dto;
import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardPhoto;
import com.WalkiePaw.domain.board.entity.PriceType;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardAddRequest {

    @NotNull
    private final Integer memberId;
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;
    // min max 정하기
    private final int price;
    @NotNull
    private final BoardCategory category;
    @NotNull
    private final PriceType priceType;
    // start < end
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private final String location;
    private final String detailedLocation;
    @NotNull
    private final boolean priceProposal;
    private List<ImageDto> photoUrls;

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
