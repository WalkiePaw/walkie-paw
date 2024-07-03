package com.WalkiePaw.domain.board.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.presentation.domain.board.BoardStatusUpdateRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardAddRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardGetResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.WalkiePaw.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<BoardListResponse> findAllBoardAndMember() {
        List<Board> findBoards = boardRepository.findAll();
        return findBoards.stream()
                .map(BoardListResponse::from)
                .toList();
    }

    @Transactional
    public Integer save(final BoardAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Board entity = BoardAddRequest.toEntity(request, member);
        return boardRepository.save(entity).getId();
    }

    public BoardGetResponse getBoard(Integer boardId) {
        Board board = boardRepository.getBoardDetail(boardId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        return BoardGetResponse.from(board);
    }

    @Transactional
    public void updateBoard(final Integer boardId, final BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        board.updateBoard(request.getTitle(), request.getContent(), request.getPrice(), request.getStartTime(),
                request.getEndTime(), request.getPriceType());
    }

    @Transactional
    public void updateBoardStatus(final BoardStatusUpdateRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        board.updateStatus(request.getStatus());


    }

    @Transactional
    public void deleteBoard(final Integer boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        board.delete();
    }
}
