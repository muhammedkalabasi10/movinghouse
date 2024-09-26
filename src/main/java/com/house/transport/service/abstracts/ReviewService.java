package com.house.transport.service.abstracts;

import com.house.transport.model.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    List<Review> fetchReviews();
    List<Review> getReviewList(int page_num, int record_num);
    Review getReviewById(Long id);
    Review updateReview(Review review);
    boolean deleteReviewById(Long id);
}
