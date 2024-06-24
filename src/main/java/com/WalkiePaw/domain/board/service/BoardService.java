package com.WalkiePaw.domain.board.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.presentation.domain.board.dto.BoardAddRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardGetResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponse> listBoardWithMember() {
        List<Board> findBoards = boardRepository.getAllBoardsWithMember();
        return findBoards.stream()
                .map(BoardListResponse::from)
                .toList();
    }

    @Transactional
    public Integer save(final BoardAddRequest request) {
        Board entity = BoardAddRequest.toEntity();
        return boardRepository.save(entity);
    }

    public BoardGetResponse getBoard(Integer boardId) {
        Board board = boardRepository.findById(boardId);
        return BoardGetResponse.from(board);
    }

    @Transactional
    public void updateBoard(final Integer boardId, final BoardUpdateRequest request) {
        boardRepository.updateBoard(boardId, request);
    }
}
