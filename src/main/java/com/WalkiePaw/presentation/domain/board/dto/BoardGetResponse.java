package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.board.entity.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardGetResponse {

    private final Integer id;
    private final BoardStatus status;
    /**
     * TODO - Member 정보 뭐 넘길지
     */
    private final String detailedLocation;
    private final String location;
    private final String title;
    private final BoardCategory category;
    private final String content;
    private final boolean priceProposal;
    private final int price;
    private final PriceType priceType;
    private final LocalDateTime endTime;
    private final LocalDateTime startTime;
    private final String memberNickName;
    private final List<String> photos;

    /**
     * BoardGetResponse 생성 메서드
     * @param board Entity
     * @return BoardGetResponse
     */
    public static BoardGetResponse from(final Board board, List<String> photos) {
        return new BoardGetResponse(board.getId(), board.getStatus(), board.getDetailedLocation(), board.getLocation(), board.getTitle(), board.getCategory(), board.getContent(), board.isPriceProposal(),
                board.getPrice(), board.getPriceType(), board.getEndTime(), board.getStartTime(), board.getMember().getNickname(), photos);
    }
}
