package com.WalkiePaw.domain.qna.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.qna.repository.QnaRepository;
import com.WalkiePaw.presentation.domain.qna.dto.QnaRequest;
import com.WalkiePaw.presentation.domain.qna.dto.QnaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<QnaResponse> findAll() {
        return qnaRepository.findAll().stream()
                .map(QnaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public QnaResponse findById(final Integer qnaId) {
        return QnaResponse.from(qnaRepository.findById(qnaId));
    }

    public Integer save(final QnaRequest request) {
        Member member = memberRepository.findById(request.getMemberId());
        return qnaRepository.save(request.toEntity(member));
    }

    public void update(final Integer qnaId, final QnaRequest request) {
        qnaRepository.update(qnaId, request);
    }
}
