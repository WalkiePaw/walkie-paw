package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.service.BoardService;
import com.WalkiePaw.presentation.domain.board.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;
    private static final String BOARD_URL = "/boards/";

    @GetMapping("/list/{category}")
    public ResponseEntity<Page<BoardListResponse>> getBoardList(
            final @PathVariable BoardCategory category,
            Pageable pageable) {
        Page<BoardListResponse> boardListResponses = boardService.findAllBoardAndMember(category, pageable);
        return ResponseEntity.ok(boardListResponses);
    }

    @GetMapping("/mypage/{memberId}/{category}")
    public ResponseEntity<Page<BoardMypageListResponse>> mypageList(
            @PathVariable Integer memberId,
            @PathVariable BoardCategory category,
            Pageable pageable
    ) {
        Page<BoardMypageListResponse> boards = boardService.findMyBoardsBy(memberId, category, pageable);
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<Void> addBoard(final @RequestBody BoardAddRequest request) {
        Integer saveId = boardService.save(request);
        return ResponseEntity.created(URI.create(BOARD_URL + saveId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGetResponse> getBoard(final @PathVariable("id") Integer boardId) {
        BoardGetResponse board = boardService.getBoard(boardId);
        return ResponseEntity.ok(board);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBoard(final @PathVariable("id") Integer boardId, final @RequestBody BoardUpdateRequest request) {
        boardService.updateBoard(boardId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<Void> updateBoardStatus(final @RequestBody BoardStatusUpdateRequest request) {
        boardService.updateBoardStatus(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(final @PathVariable("id") Integer boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BoardListResponse>> searchBoard(
            final @RequestParam(required = false) String title,
            final @RequestParam(required = false) String content,
            final @RequestParam(required = false) BoardCategory category,
            Pageable pageable
    ) {
        Page<BoardListResponse> list = boardService.findBySearchCond(title, content, category, pageable);
        return ResponseEntity.ok(list);
    }
}
