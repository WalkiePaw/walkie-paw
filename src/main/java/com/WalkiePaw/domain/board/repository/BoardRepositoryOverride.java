package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;

import java.util.List;

public interface BoardRepositoryOverride {

    List<Board> findAllNotDeleted();

    List<Board> findBySearchCond(String title, String content);
}
