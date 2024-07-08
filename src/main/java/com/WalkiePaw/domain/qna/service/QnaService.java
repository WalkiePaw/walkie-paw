package com.WalkiePaw.domain.qna.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.repository.QnaRepository;
import com.WalkiePaw.presentation.domain.qna.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return QnaGetResponse.from(qnaRepository.findById(qnaId).orElseThrow());
    }

    public Integer save(final QnaAddRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        return qnaRepository.save(request.toEntity(member)).getId();
    }

    public void update(final Integer qnaId, final QnaUpdateRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        qna.update(request);
    }

    public void updateReply(final Integer qnaId, final replyUpdateRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        qna.updateReply(request);
    }
}
