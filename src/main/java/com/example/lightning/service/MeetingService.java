package com.example.lightning.service;

import com.example.lightning.domain.Meeting;
import com.example.lightning.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public void registerMeeting(Meeting meeting) {
        // 모임 등록 로직 구현 (DB에 저장)
        System.out.println("모임 등록: " + meeting.toString());
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting getMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId).orElse(null);
    }

    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }
}
