package com.WalkiePaw.domain.board.repository;


import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class QuerydslBoardRepositoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private BoardRepositoryOverride querydslBoardRepository;
    @Autowired
    private EntityManager em;

    @Test
    void 쿼리dsl_게시글_리스트_테스트() {
        Member member1 = Member.builder().name("member1").build();
        em.persist(member1);

        Board board1 = Board.builder().member(member1).build();
        Board board2 = Board.builder().member(member1).build();
        Board board3 = Board.builder().member(member1).build();
        Board board4 = Board.builder().member(member1).build();

        em.persist(board1);
        em.persist(board2);
        em.persist(board3);
        em.persist(board4);

        board2.delete();

        em.flush();
        em.clear();

//        List<Board> allNotDeleted = querydslBoardRepository.findAllNotDeleted();

//        assertThat(allNotDeleted.size()).isEqualTo(3);
//        assertThat(allNotDeleted.contains(board2)).isFalse();
    }
}