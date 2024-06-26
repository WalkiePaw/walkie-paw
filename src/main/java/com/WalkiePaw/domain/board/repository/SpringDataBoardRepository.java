package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("spring-data-jpa")
public interface SpringDataBoardRepository extends JpaRepository<Board, Integer>, BoardRepository {
}
