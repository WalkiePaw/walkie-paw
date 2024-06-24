package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.report.repository.BoardReportRepository;
import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportRequest;
import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardReportService {

    private final BoardReportRepository boardReportRepository;

    public BoardReportResponse findById(Integer boardReportId) {
        return BoardReportResponse.from(boardReportRepository.findById(boardReportId));
    }

    public Integer save(BoardReportRequest request) {
        return boardReportRepository.save(request.toEntity());
    }

    public void update(Integer boardReportId, BoardReportRequest request) {
        boardReportRepository.update(boardReportId, request);
    }
}
