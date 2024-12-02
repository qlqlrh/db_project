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
    public boolean isConflictWithTimetable(Long userId, LocalDate date, LocalTime time) {
        return timetableRepository.existsByUser_UserIdAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                userId, date.getDayOfWeek().toString(), time, time
        );
    }
}