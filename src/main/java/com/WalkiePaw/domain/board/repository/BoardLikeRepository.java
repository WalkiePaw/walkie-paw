package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
    BoardLike findByMemberIdAndBoardId(Integer memberId, Integer boardId);

    Integer countByBoardId(Integer id);
}
