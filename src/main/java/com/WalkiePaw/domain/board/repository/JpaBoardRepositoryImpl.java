package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Profile("jpa")
public class JpaBoardRepositoryImpl implements BoardRepository {

    private final EntityManager em;

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Optional<Board> findById(Integer boardId) {
        return Optional.ofNullable(em.find(Board.class, boardId));
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

//    @Override
    public List<Board> findByBoardAndMember() {
        return em.createQuery("select b from Board b join fetch b.member", Board.class)
                .getResultList();
    }

//    @Override
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
