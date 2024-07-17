package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.presentation.domain.qna.dto.QnaListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QnaRepositoryOverride {

    Page<QnaListResponse> findAllByCond(String status, Pageable pageable);

    Page<QnaListResponse> findByMemberId(Integer memberId, Pageable pageable);
}
