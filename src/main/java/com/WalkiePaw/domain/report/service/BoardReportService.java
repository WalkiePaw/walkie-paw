package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.repository.BoardReportRepository;
import com.WalkiePaw.presentation.domain.report.boardReportDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardReportService {

    private final BoardReportRepository boardReportRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public BoardReportGetResponse findById(final Integer boardReportId) {
        return BoardReportGetResponse.from(boardReportRepository.findById(boardReportId).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<BoardReportListResponse> findAll() {
        return boardReportRepository.findAll().stream()
                .map(BoardReportListResponse::from)
                .toList();
    }

    public Integer save(final BoardReportAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Board board = boardRepository.findById(request.getBoardId());
        return boardReportRepository.save(request.toEntity(member, board)).getId();
    }

    public void update(final Integer boardReportId, final BoardReportUpdateRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Board board = boardRepository.findById(request.getBoardId());
        BoardReport boardReport = boardReportRepository.findById(boardReportId).orElseThrow();
        boardReport.update(request, member, board);
    }
}
