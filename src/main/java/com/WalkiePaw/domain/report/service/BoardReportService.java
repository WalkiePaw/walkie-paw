package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.repository.BoardReport.BoardReportRepository;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.global.exception.ExceptionCode;
import com.WalkiePaw.presentation.domain.report.boardReportDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.WalkiePaw.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardReportService {

    private final BoardReportRepository boardReportRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public BoardReportGetResponse findById(final Integer boardReportId) {
        return BoardReportGetResponse.from(boardReportRepository.findById(boardReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_REPORT_ID)
        ));
    }

    @Transactional(readOnly = true)
    public List<BoardReportListResponse> findAll() {
        return boardReportRepository.findAll().stream()
                .map(BoardReportListResponse::from)
                .toList();
    }

    public Integer save(final BoardReportAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_ID)
        );
        return boardReportRepository.save(request.toEntity(member, board)).getId();
    }

    public void update(final Integer boardReportId, final BoardReportUpdateRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_ID)
        );
        BoardReport boardReport = boardReportRepository.findById(boardReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_REPORT_ID)
        );
        boardReport.update(request, member, board);
    }

    public void blind(final Integer boardReportId) {
        BoardReport boardReport = boardReportRepository.findById(boardReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_REPORT_ID)
        );
        Board board = boardRepository.findById(boardReport.getBoard().getId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_ID)
        );
        board.delete();
        boardReport.blind();
    }

    public void ignore(final Integer boardReportId) {
        BoardReport boardReport = boardReportRepository.findById(boardReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_BOARD_REPORT_ID)
        );
        boardReport.ignore();
    }

    public Page<BoardReportListResponse> findAllByResolvedCond(final String status, Pageable pageable) {
        return boardReportRepository.findAllByResolvedCond(status, pageable);

    }
}
