package com.WalkiePaw.domain.board.entity;

import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Integer id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardLike(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

//    /**
//     * BoardLike create 메서드
//     */
//    public BoardLike createBoardLike(Member member, Board board) {
//        return new BoardLike(member, board);
//    }
}
