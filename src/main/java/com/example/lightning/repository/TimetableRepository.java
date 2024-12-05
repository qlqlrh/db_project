package com.example.lightning.repository;

import com.example.lightning.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    // Timetable 엔티티에서 user 필드의 User 객체 내부의 userId를 참조
    List<Timetable> findByUser_UserId(Long userId); // 정확한 경로 지정

    /// 사용자 ID와 요일로 시간표 검색
    List<Timetable> findByUser_UserIdAndDayOfWeek(Long userId, Timetable.DayOfWeek dayOfWeek);
}