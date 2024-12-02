package com.example.lightning.service;

import com.example.lightning.domain.Enrollment;
import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.User;
import com.example.lightning.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void saveEnrollment(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    public boolean existsByUserAndMeeting(User user, Meeting meeting) {
        return enrollmentRepository.existsByUserAndMeeting(user, meeting);
    }

    public List<Meeting> getMeetingsByUserId(Long userId) {
        return enrollmentRepository.findMeetingsByUserId(userId);
    }

}
