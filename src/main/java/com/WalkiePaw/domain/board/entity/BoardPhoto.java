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
    private String oriName;
    private String uuidName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardPhoto(String oriName, String uuidName, Board board) {
        this.board = board;
        this.oriName = oriName;
        this.uuidName = uuidName;
    }

//    /**
//     * BoardPhoto 생성 메서드
//     */
//    public BoardPhoto createBoardPhoto(String oriName, String uuidName, Board board) {
//        return new BoardPhoto(oriName, uuidName, board);
//    }
}
