package com.example.lightning.repository;

import com.example.lightning.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    // Timetable 엔티티에서 user 필드의 User 객체 내부의 userId를 참조
    List<Timetable> findByUser_UserId(Long userId); // 정확한 경로 지정

    // 해당 유저의 시간표가 존재 여부와 특정 시간과의 일치 여부 확인
    boolean existsByUser_UserIdAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long userId, String dayOfWeek, LocalTime startTime, LocalTime endTime
    );
}
