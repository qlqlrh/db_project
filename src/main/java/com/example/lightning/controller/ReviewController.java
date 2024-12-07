package com.example.lightning.controller;

import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.Review;
import com.example.lightning.domain.User;
import com.example.lightning.service.MeetingService;
import com.example.lightning.service.ReviewService;
import com.example.lightning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String reviewBoard(@RequestParam(value = "sort", defaultValue = "date") String sort,
                              @RequestParam(value = "filterFiveStars", defaultValue = "false") boolean filterFiveStars,
                              Model model, HttpSession session) {
        List<Review> reviews;

        if (filterFiveStars) {
            reviews = reviewService.getReviewsWithFiveStars(); // 별점 5점인 리뷰만 가져오기
        } else {
            if ("rating".equals(sort)) {
                reviews = reviewService.getReviewsSortedByRating(); // 별점 순으로 정렬된 리뷰
            } else if ("date".equals(sort)) {
                reviews = reviewService.getReviewsSortedByDate();
            } else {
                reviews = reviewService.getAllReviews(); // 기본은 등록 순서대로
            }
        }

        model.addAttribute("reviews", reviews);
        model.addAttribute("sort", sort);
        model.addAttribute("filterFiveStars", filterFiveStars); // 필터 상태 전달

        Long userId = (Long) session.getAttribute("userId");
        String userRole = "";
        if (userId != null) {
            User user = userService.getUserById(userId);
            userRole = String.valueOf(user.getRole());
        }

        model.addAttribute("userRole", userRole);
        return "reviewBoard";
    }


    // 후기 작성 폼
    @GetMapping("/create/{meetingId}")
    public String createReviewForm(@PathVariable Long meetingId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        Meeting meeting = meetingService.getMeetingById(meetingId);
        if (meeting == null) {
            throw new IllegalArgumentException("Invalid meeting ID");
        }

        // 사용자가 이미 후기를 작성했는지 확인
        if (reviewService.hasUserReviewedMeeting(userId, meetingId)) {
            model.addAttribute("error", "You have already submitted a review for this meeting.");
            return "redirect:/mypage";
        }

        model.addAttribute("meeting", meeting);
        return "review-form"; // 후기 작성 페이지
    }

    // 후기 등록
    @PostMapping("/save")
    public String saveReview(@RequestParam("meetingId") Long meetingId,
                             @RequestParam("rating") int rating,
                             @RequestParam("comment") String comment,
                             HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            reviewService.saveReview(userId, meetingId, rating, comment);
            model.addAttribute("message", "Review submitted successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "review-form";
        }

        return "redirect:/mypage";
    }

    // 후기 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, HttpSession session) {
        System.out.println("deleteReview");

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = userService.getUserById(userId);
        if (!user.getRole().equals(User.Role.Student_Council)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }

        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok("후기가 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}