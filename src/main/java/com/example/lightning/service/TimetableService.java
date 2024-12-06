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

    public void saveTimetable(Timetable timetable) {
        // 검증 로직: 시작 시간이 종료 시간보다 빠르지 않으면 예외 발생
        if (timetable.getStartTime().isAfter(timetable.getEndTime())) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 빨라야 합니다.");
        }
        if (isTimetableConflict(timetable.getUser().getUserId(), timetable)) {
            throw new IllegalArgumentException("이미 등록된 시간표와 시간이 겹칩니다.");
        }
        timetableRepository.save(timetable);
    }

    public List<Timetable> getTimetablesByUserId(Long userId) {

        return timetableRepository.findByUser_UserId(userId);
    }

    // 시간표와 중복되는 모임인지 체크
    public boolean isConflictWithTimetable(Long userId, Timetable.DayOfWeek dayOfWeek, LocalTime meetingStartTime, LocalTime meetingEndTime) {

        // 사용자의 시간표에서 모임과 동일한 요일에 있는 모든 시간표를 가져옴
        List<Timetable> timetables = timetableRepository.findByUser_UserIdAndDayOfWeek(userId, dayOfWeek);

        for (Timetable timetable : timetables) {
            LocalTime startTime = timetable.getStartTime();
            LocalTime endTime = timetable.getEndTime();

            // 모임 시간이 시간표와 겹치는지 확인
            if ((meetingStartTime.isBefore(endTime) && meetingStartTime.isAfter(startTime)) ||  // 모임 시작 시간이 시간표 사이에 있음
                    (meetingEndTime.isAfter(startTime) && meetingEndTime.isBefore(endTime)) ||  // 모임 종료 시간이 시간표 사이에 있음
                    (meetingStartTime.equals(startTime) || meetingEndTime.equals(endTime))) {   // 모임 시간이 시간표 시작 또는 종료 시간과 일치
                return true; // 충돌 발생
            }
        }
        return false;
    }

    public boolean isTimetableConflict(Long userId, Timetable newTimetable) {
        List<Timetable> existingTimetables = timetableRepository.findByUser_UserIdAndDayOfWeek(userId, newTimetable.getDayOfWeek());

        for (Timetable existingTimetable : existingTimetables) {
            LocalTime newStartTime = newTimetable.getStartTime();
            LocalTime newEndTime = newTimetable.getEndTime();
            LocalTime existingStartTime = existingTimetable.getStartTime();
            LocalTime existingEndTime = existingTimetable.getEndTime();

            // Check if the new timetable overlaps with any existing timetable
            if ((newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime)) ||  // New timetable overlaps with an existing timetable
                    (newStartTime.equals(existingStartTime) || newEndTime.equals(existingEndTime))) {   // New timetable starts or ends exactly at the same time as an existing timetable
                return true; // Conflict detected
            }
        }
        return false; // No conflict
    }
}