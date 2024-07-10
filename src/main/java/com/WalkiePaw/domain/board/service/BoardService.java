package com.WalkiePaw.domain.board.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardPhoto;
import com.WalkiePaw.domain.board.repository.BoardPhotoRepository;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.presentation.domain.board.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final BoardPhotoRepository boardPhotoRepository;

    public Slice<BoardListResponse> findAllBoardAndMember(final BoardCategory category, Pageable pageable) {
        return boardRepository.findAllNotDeleted(category, pageable);
    }

    @Transactional
    public Integer save(final BoardAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Board entity = BoardAddRequest.toEntity(request, member);
        Board board = boardRepository.save(entity);
        createBoardPhoto(request, board);
        return board.getId();
    }

    private void createBoardPhoto(final BoardAddRequest request, final Board board) {
        request.getPhotos().stream()
                .map(i -> new BoardPhoto(i.getUrl()))
                .map(boardPhotoRepository::save)
                .toList().forEach(p -> p.addPhoto(board));
    }


    public BoardGetResponse getBoard(Integer boardId) {
        Board board = boardRepository.getBoardDetail(boardId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        List<String> photoUrls = board.getPhotoUrls(board);
        return BoardGetResponse.from(board);
    }

    @Transactional
    public void updateBoard(final Integer boardId, final BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
        board.updateBoard(request.getTitle(), request.getContent(), request.getPrice(), request.getStartTime(),
                request.getEndTime(), request.getPriceType(), request.getLocation(), request.getDetailedLocation(), request.isPriceProposal());
        board.updatePhoto(request.getPhotos().stream().map(ImageDto::getUrl).toList());
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

    public Slice<BoardListResponse> findBySearchCond(final String title, final String content, final BoardCategory category, Pageable pageable) {
        return boardRepository.findBySearchCond(title, content, category, pageable);
    }

    public Page<BoardMypageListResponse> findMyBoardsBy(final Integer memberId, final BoardCategory category, Pageable pageable) {
        return boardRepository.findMyBoardsBy(memberId, category, pageable);
    }
//
//    @Transactional
//    public Integer uploadPhoto(final Integer boardId, final String uploadURL, final String oriName) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new BadRequestException(NOT_FOUND_BOARD_ID));
//        BoardPhoto boardPhoto = new BoardPhoto(oriName, uploadURL, board);
//        boardPhoto.addPhoto(board);
//        return boardPhotoRepository.save(boardPhoto).getId();
//    }
}
