package com.WalkiePaw.domain.board.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardLike;
import com.WalkiePaw.domain.board.repository.BoardLikeRepository;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.board.dto.BoardLikeRequest;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Slice<BoardListResponse> findLikeBoardList(final Integer memberId, final Pageable pageable) {
        return boardRepository.findLikeBoardList(memberId, pageable);
    }

    public Integer saveBoardLike(final BoardLikeRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow();
        Member member = memberRepository.findById(request.getLoginUserId()).orElseThrow();
        BoardLike boardLike = new BoardLike(member, board);
        return boardLikeRepository.save(boardLike).getId();
    }

    public void cancelBoardLike(final BoardLikeRequest request) {
        BoardLike boardLike = boardLikeRepository.findByMemberIdAndBoardId(request.getLoginUserId(), request.getBoardId());
        boardLikeRepository.delete(boardLike);
    }

    @Scheduled(fixedDelay = 600000)
    public void countBoardLike() {
        List<Board> boards = boardRepository.findAll();
        boards.forEach(b -> b.updateBoardLike(boardLikeRepository.countByBoardId(b.getId())));
    }
}
