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
        meetingRepository.save(meeting); // Save the meeting to the database
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