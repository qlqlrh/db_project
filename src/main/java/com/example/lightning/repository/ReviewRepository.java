package com.example.lightning.repository;

import com.example.lightning.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /*
        Review 엔티티의 user 필드와 User 엔티티의 userId 필드를 참조
        AND
        Review 엔티티의 meeting 필드와 Meeting 엔티티의 meetingId 필드
     */
    boolean existsByUser_UserIdAndMeeting_MeetingId(Long userId, Long meetingId);

    boolean existsByMeeting_MeetingIdAndUser_UserId(Long meetingId, Long userId);

    // 별점 높은 순으로 정렬
    List<Review> findAllByOrderByRatingDesc();

    List<Review> findAllByOrderByMeeting_DateDesc();
}
