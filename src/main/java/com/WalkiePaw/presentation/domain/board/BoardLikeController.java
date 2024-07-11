package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.service.BoardLikeService;
import com.WalkiePaw.domain.board.service.BoardService;
import com.WalkiePaw.presentation.domain.board.dto.BoardLikeRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards-like")
public class BoardLikeController {

    public static final String BOARDS_LIKE = "/boards-like/";
    private final BoardLikeService boardLikeService;
    private final BoardService boardService;

    @GetMapping("/{memberId}")
    public ResponseEntity<Slice<BoardListResponse>> findLikeBoardList(final @PathVariable Integer memberId, Pageable pageable) {
        Slice<BoardListResponse> likeBoardList = boardLikeService.findLikeBoardList(memberId, pageable);
        return ResponseEntity.ok(likeBoardList);
    }

    /**
     * TODO - 좋아요 갯수 등 처리
     */
    @PostMapping
    public ResponseEntity<Void> addBoardLike(final @RequestBody BoardLikeRequest request) {
        Integer id = boardLikeService.saveBoardLike(request);
        return ResponseEntity.created(URI.create(BOARDS_LIKE + id)).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelBoardLike(final @RequestBody BoardLikeRequest request) {
        boardLikeService.cancelBoardLike(request);
        return ResponseEntity.noContent().build();
    }
}
