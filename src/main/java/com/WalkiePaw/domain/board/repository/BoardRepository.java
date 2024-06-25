package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepository {

    private final EntityManager em;

    public Integer save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findById(Integer boardId) {
        return em.find(Board.class, boardId);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public List<Board> findAllBoardAndMember() {
        return em.createQuery("select b from Board b join fetch b.member", Board.class)
                .getResultList();
    }

    public void updateBoard(final Integer boardId, final BoardUpdateRequest request) {
        Board board = em.find(Board.class, boardId);
        board.updateBoard(request.getContent(),
                request.getTitle(),
                request.getPrice(),
                request.getMeetingTime(),
                request.getStartTime(),
                request.getEndTime(),
                request.getPriceType());
    }
}
