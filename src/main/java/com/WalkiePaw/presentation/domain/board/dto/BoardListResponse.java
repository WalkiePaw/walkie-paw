package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.board.entity.PriceType;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardListResponse {
    private final Integer id;
    private final String title;
    private final String content;
    private final String location;

    private final int price;
    private final PriceType priceType;
    private final LocalDateTime endTime;
    private final LocalDateTime startTime;
    private final int likeCount;

    private final String memberNickName;
    private final BoardStatus status;
    private final BoardCategory category;
    private final boolean priceProposal;

    private final List<String> photos;

    public static BoardListResponse from(final Board board) {
        return new BoardListResponse(
                board.getId(), board.getTitle(), board.getContent(), board.getLocation(),
                board.getPrice(), board.getPriceType(), board.getEndTime(), board.getStartTime(), board.getLikeCount(),
                board.getMember().getNickname(), board.getStatus(), board.getCategory(), board.isPriceProposal(), board.getPhotoUrls(board));
    }

}
