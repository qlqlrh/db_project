package com.example.lightning.service;

import com.example.lightning.domain.Review;
import com.example.lightning.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

}