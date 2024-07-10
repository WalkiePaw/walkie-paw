package com.WalkiePaw.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_photo_id")
    private Integer id;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardPhoto(String url, Board board) {
        this.board = board;
        this.url = url;
    }

    public BoardPhoto(final String url) {
        this.url = url;
    }

    public void addPhoto(Board board) {
        this.board = board;
        board.getPhotos().add(this);
    }

//    /**
//     * BoardPhoto 생성 메서드
//     */
//    public BoardPhoto createBoardPhoto(String oriName, String uuidName, Board board) {
//        return new BoardPhoto(oriName, uuidName, board);
//    }
}
