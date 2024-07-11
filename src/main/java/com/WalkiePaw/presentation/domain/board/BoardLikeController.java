package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.service.BoardLikeService;
import com.WalkiePaw.domain.board.service.BoardService;
import com.WalkiePaw.presentation.domain.board.dto.BoardLikeAddRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
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

    @PostMapping
    public ResponseEntity<Void> addBoardLike(final BoardLikeAddRequest request) {
        Integer id = boardLikeService.saveBoardLike(request);
        return ResponseEntity.created(URI.create(BOARDS_LIKE + id)).build();
    }
}
