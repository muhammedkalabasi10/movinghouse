package com.house.transport.service.abstracts;

import com.house.transport.model.Mover;
import com.house.transport.model.Review;

import java.util.List;

public interface ReviewService {
    public abstract Review createReview(Review review);
    public abstract List<Review> fetchReviews();
    public abstract Review getReviewById(Long id);
    public abstract Review updateReview(Review review);
    public abstract boolean deleteReviewById(Long id);
}
