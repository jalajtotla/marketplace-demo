package com.example.marketplace.review.controller;

import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.review.controller.dto.CreateReviewRequest;
import com.example.marketplace.review.controller.dto.EvaluateReviewRequest;
import com.example.marketplace.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import com.example.marketplace.auth.AuthRequired;
import com.example.marketplace.user.service.LoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    @Qualifier("userSessionLoginService")
    private final LoginService loginService;


    @AuthRequired
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessResponse createReview(@Valid CreateReviewRequest dto){
        Long userId = loginService.getLoginUserId();
        reviewService.createReview(userId, dto);
        return SuccessResponse.builder()
                .message("Review created")
                .build();
    }

    @AuthRequired
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/evaluate")
    public SuccessResponse evaluateReview(@Valid @RequestBody EvaluateReviewRequest dto){
        Long userId = loginService.getLoginUserId();
        reviewService.evaluateReview(userId, dto);
        return SuccessResponse.builder()
                .status(StatusEnum.CREATED)
                .message("Review evaluated")
                .build();
    }
}