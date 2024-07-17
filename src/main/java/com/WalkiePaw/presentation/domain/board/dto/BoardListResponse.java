package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
    @NoArgsConstructor
public class BoardListResponse {
    private Integer id;
    private String title;
    private String content;
    private String location;

    private int price;
    private PriceType priceType;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private int likeCount;

    private String memberNickName;
    private BoardStatus status;
    private BoardCategory category;
    private boolean priceProposal;

    private String photoUrls;
    private String memberPhoto;

    private boolean isLiked;

    public BoardListResponse(Integer id, String title, String content, String location, int price, PriceType priceType,
                             LocalDateTime endTime, LocalDateTime startTime, int likeCount, String memberNickName,
                             BoardStatus status, BoardCategory category, boolean priceProposal,
                             String memberPhoto, boolean isLiked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.location = location;
        this.price = price;
        this.priceType = priceType;
        this.endTime = endTime;
        this.startTime = startTime;
        this.likeCount = likeCount;
        this.memberNickName = memberNickName;
        this.status = status;
        this.category = category;
        this.priceProposal = priceProposal;
        this.memberPhoto = memberPhoto;
        this.isLiked = isLiked;
    }

    public BoardListResponse(final Integer id, final String title, final String content, final String location, final int price, final PriceType priceType, final LocalDateTime endTime, final LocalDateTime startTime, final int likeCount, final String memberNickName, final BoardStatus status, final BoardCategory category, final boolean priceProposal, final String photoUrls, final String memberPhoto, final boolean isLiked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.location = location;
        this.price = price;
        this.priceType = priceType;
        this.endTime = endTime;
        this.startTime = startTime;
        this.likeCount = likeCount;
        this.memberNickName = memberNickName;
        this.status = status;
        this.category = category;
        this.priceProposal = priceProposal;
        this.photoUrls = photoUrls;
        this.memberPhoto = memberPhoto;
        this.isLiked = isLiked;
    }

    public static BoardListResponse from(final Board board, final boolean isLiked) {
        String photoUrls = null;
        if (!board.getPhotos().isEmpty()) {
            photoUrls = board.getPhotoUrls(board).getFirst();
        }
        return new BoardListResponse(
                board.getId(), board.getTitle(), board.getContent(), board.getLocation(),
                board.getPrice(), board.getPriceType(), board.getEndTime(), board.getStartTime(), board.getLikeCount(),
                board.getMember().getNickname(), board.getStatus(),
                board.getCategory(), board.isPriceProposal(), photoUrls, board.getMember().getPhoto(), isLiked);
    }

}
