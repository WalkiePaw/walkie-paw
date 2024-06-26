package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<Board> findAll();

    List<Board> findBoardAndMemberBy();

    Board save(Board board);

    Optional<Board> findById(Integer id);

    Optional<Board> getBoardDetail(Integer boardId);
}
