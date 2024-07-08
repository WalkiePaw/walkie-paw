package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardRepositoryOverride {

    List<Board> findAllNotDeleted(BoardCategory category);

    List<Board> findBySearchCond(String title, String content, BoardCategory category);

    List<BoardMypageListResponse> findMyBoardsBy(Integer memberId, BoardCategory category);

    Slice<BoardListResponse> findLikeBoardList(Integer memberId, Pageable pageable);
}
