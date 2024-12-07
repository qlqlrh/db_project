package com.example.lightning.service;

import com.example.lightning.domain.Enrollment;
import com.example.lightning.domain.Meeting;
import com.example.lightning.repository.EnrollmentRepository;
import com.example.lightning.repository.MeetingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    // 모임 날짜 순으로 정렬(index)
    public List<Meeting> getMeetingsSortedByDate() {
        return meetingRepository.findAllByOrderByDateAsc();
    }

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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

    public List<Meeting> getMeetingsByCreator(Long userId) {
        return meetingRepository.findByUser_UserId(userId);
    }

    public void completeMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting ID"));
        meeting.setCompleted(true);
        meetingRepository.save(meeting);
    }

    @Transactional
    // 모임 개설 취소 (모임을 삭제하면 신청자들도 삭제)
    public void cancelCreatedMeeting(Long meetingId) {
        // 해당 모임을 찾아서
        Optional<Meeting> meeting = meetingRepository.findById(meetingId);

        if (meeting.isPresent()) {
            // 해당 모임에 대한 모든 신청자 삭제
            List<Enrollment> enrollments = enrollmentRepository.findByMeeting_MeetingId(meetingId);
            enrollmentRepository.deleteAll(enrollments);

            // 모임 삭제
            meetingRepository.delete(meeting.get());
        }
    }

    @Transactional
    // 모임 신청 취소
    public void cancelEnrolledMeeting(Long userId, Long meetingId) {
        // 모임 신청 취소 처리
        Optional<Enrollment> enrollment = enrollmentRepository.findByUser_UserIdAndMeeting_MeetingId(userId, meetingId);

        if (enrollment.isPresent()) {
            // 참가자 수 감소
            Meeting meeting = enrollment.get().getMeeting();
            meeting.setParticipantCount(meeting.getParticipantCount() - 1);
            meetingRepository.save(meeting);

            // 신청 취소된 엔트리 삭제
            enrollmentRepository.delete(enrollment.get());
        }
    }
}