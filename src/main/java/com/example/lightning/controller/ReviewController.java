package com.example.lightning.controller;

import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.Review;
import com.example.lightning.domain.User;
import com.example.lightning.service.MeetingService;
import com.example.lightning.service.ReviewService;
import com.example.lightning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MeetingService meetingService;

    // 후기 게시판 띄움
    @GetMapping("")
    public String reviewBoard(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviewBoard";
    }

    // 후기 작성 폼 띄움
    @GetMapping("/create/{meetingId}")
    public String createReviewForm(@PathVariable Long meetingId, Model model) {
        Meeting meeting = meetingService.getMeetingById(meetingId);
        if (meeting == null) {
            throw new IllegalArgumentException("Invalid meeting ID");
        }
        model.addAttribute("meeting", meeting);
        return "review-form";
    }

    // 후기 등록
    @PostMapping("/save")
    public String saveReview(@RequestParam("meetingId") Long meetingId,
                             @RequestParam("rating") int rating,
                             @RequestParam("comment") String comment,
                             HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        reviewService.saveReview(userId, meetingId, rating, comment);
        return "redirect:/reviews";
    }


}
