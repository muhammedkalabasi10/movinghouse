package com.house.transport.service.abstracts;

import com.house.transport.model.Review;

import java.util.List;

public interface ReviewService {
    abstract Review createReview(Review review);
    abstract List<Review> fetchReviews();
    List<Review> getReviewList(int page_num, int record_num);
    abstract Review getReviewById(Long id);
    abstract Review updateReview(Review review);
    abstract boolean deleteReviewById(Long id);
}
