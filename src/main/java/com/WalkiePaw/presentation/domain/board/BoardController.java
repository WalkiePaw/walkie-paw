package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.service.BoardService;
import com.WalkiePaw.presentation.domain.board.dto.BoardAddRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardGetResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
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

    @GetMapping
    public ResponseEntity<List<BoardListResponse>> getBoardList() {
        List<BoardListResponse> boardListResponses = boardService.findAllBoardAndMember();
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
}
