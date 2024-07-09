package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.BoardPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPhotoRepository extends JpaRepository<BoardPhoto, Integer> {
}
