package com.example.marketplace.review.repository;

import com.example.marketplace.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewRepository {

    long insertReview(Review review);

}
