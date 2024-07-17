package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Profile("spring-data-jpa")
public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositoryOverride {

    @Query("select b from Board b join fetch b.member m where b.id = :id")
    Optional<Board> getBoardDetail(@Param("id") Integer boardId);

    Set<Board> findAllByIdIn(Set<Integer> integers);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.likeCount = :likeCount WHERE b.id = :id")
    int updateLikeCountById(@Param("likeCount") Integer likes, @Param("id") Integer id);
}
