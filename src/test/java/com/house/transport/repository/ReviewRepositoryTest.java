package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Review;
import jakarta.transaction.Transactional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MoverRepository moverRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createReviewSuccess(){
        Review review = new Review(null, null, null, 3, "test comment");
        Review createdReview = reviewRepository.save(review);
        assertThat(createdReview)
                .extracting(
                        Review::getEvaluation,
                        Review::getComment
                ).containsExactly(
                        createdReview.getEvaluation(),
                        createdReview.getComment()
                );
    }

    @Test
    public void fetchReviews(){
        List<Review> reviewList = new ArrayList<>();
        int listSize = 3;
        for(int i=0; i<listSize; i++){
            Review review = new Review(null, null, null, i%5, "test comment "+i);
            reviewList.add(review);
        }
        reviewRepository.saveAllAndFlush(reviewList);
        List<Review> fetchedReviewList = reviewRepository.findAll();
        int testReviewIndex = 0;
        assertThat(fetchedReviewList)
                .hasSize(listSize)
                .extracting(
                        Review::getEvaluation,
                        Review::getComment
                ).contains(new Tuple(
                        reviewList.get(testReviewIndex).getEvaluation(),
                        reviewList.get(testReviewIndex).getComment()
                ));
    }

    @Test
    public void getReviewByIdSuccess(){
        Review review = new Review(null, null, null, 3, "test comment");
        Review createdReview = reviewRepository.save(review);
        Optional<Review> foundReview = reviewRepository.findById(createdReview.getId());
        assertThat(foundReview).isPresent().get()
                .extracting(
                        Review::getEvaluation,
                        Review::getComment
                ).containsExactly(
                        createdReview.getEvaluation(),
                        createdReview.getComment()
                );
    }

    @Test
    public void updateReviewSuccess(){
        Review review = new Review(null, null, null, 3, "test comment");
        Review createdReview = reviewRepository.save(review);
        createdReview.setComment("updated comment");
        createdReview.setEvaluation(5);
        Review updatedReview = reviewRepository.save(createdReview);
        assertThat(updatedReview)
                .extracting(
                        Review::getEvaluation,
                        Review::getComment
                ).containsExactly(
                        createdReview.getEvaluation(),
                        createdReview.getComment()
                );
    }

    @Test
    public void deleteReview(){
        Review review = new Review(null, null, null, 3, "test comment");
        Review createdReview = reviewRepository.save(review);
        reviewRepository.delete(createdReview);
        Optional<Review> deletedReview = reviewRepository.findById(createdReview.getId());
        assertThat(deletedReview).isNotPresent();
    }
}
