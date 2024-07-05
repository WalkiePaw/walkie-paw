package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.service.BoardService;
import com.WalkiePaw.presentation.domain.board.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;
    private static final String BOARD_URL = "/boards/";

    @GetMapping("/list/{category}")
    public ResponseEntity<List<BoardListResponse>> getBoardList(final @PathVariable BoardCategory category) {
        List<BoardListResponse> boardListResponses = boardService.findAllBoardAndMember(category);
        return ResponseEntity.ok(boardListResponses);
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

    @PatchMapping("/status")
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
    public ResponseEntity<List<BoardListResponse>> searchBoard(final @RequestBody BoardSearchRequest request) {
        List<BoardListResponse> list = boardService.findBySearchCond(request);
        return ResponseEntity.ok(list);
    }
}
