package com.example.lightning.repository;

import com.example.lightning.domain.Enrollment;
import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndMeeting(User user, Meeting meeting);

    @Query("SELECT e.meeting FROM Enrollment e WHERE e.user.userId = :userId")
    List<Meeting> findMeetingsByUserId(@Param("userId") Long userId);

    Optional<Enrollment> findByUser_UserIdAndMeeting_MeetingId(Long userId, Long meetingId);

    List<Enrollment> findByMeeting_MeetingId(Long meetingId);
}
