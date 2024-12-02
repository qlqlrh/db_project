package com.example.lightning.controller;

import com.example.lightning.domain.Enrollment;
import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.User;
import com.example.lightning.service.EnrollmentService;
import com.example.lightning.service.MeetingService;
import com.example.lightning.service.TimetableService;
import com.example.lightning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerMeetingPage(HttpSession session, Model model) {
        // 세션에서 userId 확인
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다."); // 에러 메시지 추가
            return "login"; // 로그인 페이지로 이동
        }

        // 로그인한 사용자 정보 가져오기
        User user = userService.getUserById(userId); // 서비스로 사용자 정보 조회
        model.addAttribute("user", user);

        return "registerMeeting";
    }

    @PostMapping("/register")
    public String registerMeeting(@ModelAttribute Meeting meeting, Model model) {
        try {
            meetingService.registerMeeting(meeting);
            model.addAttribute("message", "모임이 성공적으로 등록되었습니다!");
            return "redirect:/meetings/apply";
        } catch (Exception e) {
            model.addAttribute("error", "모임 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "registerMeeting";
        }
    }

    // 모임 신청 페이지
    @GetMapping("/apply")
    public String showMeetingList(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("meetings", meetings);
        return "meeting-apply"; // HTML 파일 이름
    }

    // 실제 모임 신청
    @PostMapping("/apply/{meetingId}")
    public String applyForMeeting(@PathVariable Long meetingId, HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to apply for a meeting.");
            return "redirect:/login";
        }

        // 사용자와 모임 정보 가져오기
        User user = userService.getUserById(userId);
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting == null || user == null) {
            model.addAttribute("error", "Invalid meeting or user.");
            return "redirect:/meetings/apply";
        }

        // 모임 날짜로부터 요일 가져오기
        String dayOfWeekString = meeting.getDate().getDayOfWeek().toString();

        // 모임 시작 시간 가져오기
        LocalTime startTime = meeting.getTime();
        LocalTime endTime = startTime.plusHours(2); // 예시: 모임이 2시간이라고 가정

        // 중복 신청 확인
        if (enrollmentService.existsByUserAndMeeting(user, meeting)) {
            model.addAttribute("error", "You have already applied for this meeting.");
            return "redirect:/meetings/apply?error=duplicate";
        }

        // 시간표 충돌 확인
        if (timetableService.isConflictWithTimetable(userId, dayOfWeekString, startTime, endTime)) {
            model.addAttribute("error", "The meeting conflicts with your timetable.");
            return "redirect:/meetings/apply?error=conflict";
        }

        // 최대 인원 초과 확인
        if (meeting.getParticipantCount() >= meeting.getMaxParticipants()) {
            model.addAttribute("error", "The meeting is already full.");
            return "redirect:/meetings/apply?error=full";
        }

        // 모임 신청 처리
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setMeeting(meeting);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollmentService.saveEnrollment(enrollment);

        // 참가자 수 증가
        meeting.setParticipantCount(meeting.getParticipantCount() + 1);
        meetingService.saveMeeting(meeting);

        model.addAttribute("message", "Successfully applied for the meeting!");
        return "redirect:/meetings/apply?success=true";
    }


}
