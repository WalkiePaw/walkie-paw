package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface BoardRepository extends JpaRepository<Board, Integer>{

    @Query("select b from Board b join fetch b.member m where b.id = :id")
    Optional<Board> getBoardDetail(@Param("id") Integer boardId);

}
