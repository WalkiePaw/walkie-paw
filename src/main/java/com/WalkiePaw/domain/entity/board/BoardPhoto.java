package com.WalkiePaw.domain.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPhoto {
    @Id
    @GeneratedValue
    @Column(name = "board_photo_id")
    private Integer id;
    private String oriName;
    private String uuidName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
