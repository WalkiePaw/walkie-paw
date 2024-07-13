package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;

import java.util.List;

public interface QnaRepositoryOverride {

    List<Qna> findAllByCond(String status);

    List<Qna> findByMemberId(Integer memberId);
}
