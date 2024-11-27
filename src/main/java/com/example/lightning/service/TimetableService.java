package com.example.lightning.service;

import com.example.lightning.domain.Timetable;
import com.example.lightning.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}