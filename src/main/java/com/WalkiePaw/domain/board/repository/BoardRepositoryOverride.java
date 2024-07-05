package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;

import java.util.List;

public interface BoardRepositoryOverride {

    List<Board> findAllNotDeleted(BoardCategory category);

    List<Board> findBySearchCond(String title, String content);

    List<BoardMypageListResponse> findMyBoardsBy(Integer memberId, BoardCategory category);
}
