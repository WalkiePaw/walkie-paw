package com.WalkiePaw.domain.review.repository;

import static com.WalkiePaw.domain.review.entity.QReview.*;

import com.WalkiePaw.domain.review.entity.QReview;
import com.WalkiePaw.domain.review.entity.Review;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import org.hibernate.query.Page;

public class ReviewRepositoryOverrideImpl extends Querydsl4RepositorySupport implements ReviewRepositoryOverride{

    public ReviewRepositoryOverrideImpl() {
        super(Review.class);
    }

}
