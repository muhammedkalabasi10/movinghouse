package com.house.transport.service.concretes;

import com.house.transport.model.Review;
import com.house.transport.repository.ReviewRepository;
import com.house.transport.service.abstracts.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> fetchReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewList(int page_num, int record_num) {
        Pageable pageable = PageRequest.of(page_num, record_num);
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return reviewPage.toList();
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public boolean deleteReviewById(Long id) {
        if(reviewRepository.existsById(id)){
            reviewRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
