package com.example.lightning.service;

import com.example.lightning.domain.Meeting;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    public void registerMeeting(Meeting meeting) {
        // 모임 등록 로직 구현 (DB에 저장)
        System.out.println("모임 등록: " + meeting.toString());
    }
}
