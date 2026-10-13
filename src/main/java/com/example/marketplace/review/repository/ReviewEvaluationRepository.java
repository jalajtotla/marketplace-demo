package com.example.marketplace.review.repository;

import com.example.marketplace.review.domain.ReviewEvaluation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface ReviewEvaluationRepository {

    void insertReviewEvaluation(ReviewEvaluation reviewEvaluation);

    Optional<ReviewEvaluation> getReviewEvaluationByUserIdAndReviewId(long userId, long reviewId);

    void updateReviewEvaluation(long userId, long reviewId, boolean isHelp);
}
