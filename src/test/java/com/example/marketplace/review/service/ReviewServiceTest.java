package com.example.marketplace.review.service;

import com.example.marketplace.global.fixture.ImageFixture.*;
import com.example.marketplace.global.fixture.ReviewEvaluationFixture.*;
import com.example.marketplace.global.fixture.ReviewFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.global.util.storage.Storage;
import com.example.marketplace.review.controller.dto.CreateReviewRequest;
import com.example.marketplace.review.controller.dto.EvaluateReviewRequest;
import com.example.marketplace.review.domain.Evaluation;
import com.example.marketplace.review.domain.ReviewEvaluation;
import com.example.marketplace.review.repository.ReviewEvaluationRepository;
import com.example.marketplace.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewEvaluationRepository reviewEvaluationRepository;

    @Mock
    private Storage storage;

    @DisplayName("Creates a review.")
    @Test
    void createReview() {
        // given
        final long userId = User1.ID;
        final CreateReviewRequest dto = CreateReviewRequest.builder()
                .productId(Review1.PRODUCT_ID)
                .score(Review1.SCORE)
                .content(Review1.CONTENT)
                .img(Image1.MULTIPART_FILE)
                .build();

        // when
        reviewService.createReview(userId, dto);

        // then
        then(reviewRepository).should(times(1)).insertReview(any());
        then(storage).should(times(1)).saveMultipartFile(any(), any());

    }

    @DisplayName("Deletes the existing evaluation before adding a new one.")
    @Test
    void evaluateReviewAgain() {
        // given
        final long userId = ReviewEvaluation1.USER_ID;
        final EvaluateReviewRequest dto = EvaluateReviewRequest.builder()
                .reviewId(ReviewEvaluation1.REVIEW_ID)
                .evaluation(Evaluation.HELP)
                .build();
        final Optional<ReviewEvaluation> reviewEvaluation = Optional.of(ReviewEvaluation1.REVIEW_EVALUATION);
        // given(reviewEvaluationRepository.getReviewEvaluationByUserIdAndReviewId(any(), any())).willReturn(reviewEvaluation);

        // when
        reviewService.evaluateReview(userId, dto);

        // then
        // then(reviewEvaluationRepository).should(times(1)).updateReviewEvaluation(any(), any(), any());
    }

    @DisplayName("Adds an evaluation when none exists.")
    @Test
    void evaluateReviewFirstTime() {
        // given
        final long userId = ReviewEvaluation1.USER_ID;
        final EvaluateReviewRequest dto = EvaluateReviewRequest.builder()
                .reviewId(ReviewEvaluation1.REVIEW_ID)
                .evaluation(Evaluation.HELP)
                .build();
        final Optional<ReviewEvaluation> notExistReviewEvaluation = Optional.ofNullable(null);
        // given(reviewEvaluationRepository.getReviewEvaluationByUserIdAndReviewId(any(), any())).willReturn(notExistReviewEvaluation);

        // when
        reviewService.evaluateReview(userId, dto);

        // then
        then(reviewEvaluationRepository).should(times(1)).insertReviewEvaluation(any());
    }
}