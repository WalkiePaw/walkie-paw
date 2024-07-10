package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardRepositoryOverride {

    Slice<BoardListResponse> findAllNotDeleted(BoardCategory category, Pageable pageable);

    Slice<BoardListResponse> findBySearchCond(String title, String content, BoardCategory category, Pageable pageable);

    Page<BoardMypageListResponse> findMyBoardsBy(Integer memberId, BoardCategory category, Pageable pageable);

    Slice<BoardListResponse> findLikeBoardList(Integer memberId, Pageable pageable);
}
