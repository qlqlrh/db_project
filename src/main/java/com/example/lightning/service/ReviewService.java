package com.example.lightning.service;

import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.Review;
import com.example.lightning.domain.User;
import com.example.lightning.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 후기 저장 로직
    public void saveReview(Long userId, Long meetingId, int rating, String comment) {
        User user = userService.getUserById(userId);
        Meeting meeting = meetingService.getMeetingById(meetingId);

        Review review = new Review();
        review.setUser(user);
        review.setMeeting(meeting);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }


    public boolean hasUserReviewed(Long userId, Long meetingId) {
        return reviewRepository.existsByUser_UserIdAndMeeting_MeetingId(userId, meetingId);
    }

    public boolean hasUserReviewedMeeting(Long userId, Long meetingId) {
        return reviewRepository.existsByMeeting_MeetingIdAndUser_UserId(meetingId, userId);
    }

    // 별점순 정렬
    public List<Review> getReviewsSortedByRating() {
        return reviewRepository.findAllByOrderByRatingDesc(); // 별점 높은 순
    }
}