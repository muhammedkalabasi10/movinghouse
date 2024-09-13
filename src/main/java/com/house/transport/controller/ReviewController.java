package com.house.transport.controller;

import com.house.transport.model.Review;
import com.house.transport.service.abstracts.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> fetchReviews(){
        return ResponseEntity.ok(reviewService.fetchReviews());
    }

   @GetMapping("{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id){
        Review review = reviewService.getReviewById(id);
        if(review != null)
            return ResponseEntity.ok(review);
        else
            return null; //NotFoundException exception will be create
    }

    @PutMapping
    public ResponseEntity<Review> updateReview(@RequestBody @Valid Review review){
        return ResponseEntity.ok(reviewService.updateReview(review));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable Long id){
        if(reviewService.deleteReviewById(id))
            return ResponseEntity.ok(true);
        else
            return null; //NotFoundException exception will be create
    }
}
