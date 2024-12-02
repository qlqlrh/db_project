package com.example.lightning.service;

import com.example.lightning.domain.Timetable;
import com.example.lightning.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public Timetable saveTimetable(Timetable timetable) {

        return timetableRepository.save(timetable);
    }

    public List<Timetable> getTimetablesByUserId(Long userId) {

        return timetableRepository.findByUser_UserId(userId);
    }

    // 시간표와 중복되는 모임인지 체크
    public boolean isConflictWithTimetable(Long userId, String dayOfWeekString, LocalTime startTime, LocalTime endTime) {

        // String 값을 Enum으로 변환
        Timetable.DayOfWeek dayOfWeek = Timetable.DayOfWeek.valueOf(dayOfWeekString.toUpperCase());

        // Repository 호출 시 Enum 값 사용
        return timetableRepository.existsByUser_UserIdAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                userId, dayOfWeek, startTime, endTime
        );
    }
}