package com.WalkiePaw.domain.qna.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.repository.QnaRepository;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.presentation.domain.qna.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.WalkiePaw.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;
import static com.WalkiePaw.global.exception.ExceptionCode.NOT_FOUND_QNA_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<QnaListResponse> findAll() {
        return qnaRepository.findAll().stream()
                .map(QnaListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public QnaGetResponse findById(final Integer qnaId) {
        return QnaGetResponse.from(qnaRepository.findById(qnaId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_QNA_ID)
        ));
    }

    public Integer save(final QnaAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        return qnaRepository.save(request.toEntity(member)).getId();
    }

    public void update(final Integer qnaId, final QnaUpdateRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_QNA_ID)
        );
        qna.update(request);
    }

    public void updateReply(final Integer qnaId, final replyUpdateRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_QNA_ID)
        );
        qna.updateReply(request);
    }

    public Page<QnaListResponse> findAllByCond(final String status, Pageable pageable) {
        return qnaRepository.findAllByCond(status, pageable);
    }

    public Page<QnaListResponse> findByMemberId(final Integer memberId, Pageable pageable) {
        return qnaRepository.findByMemberId(memberId, pageable);
    }
}
