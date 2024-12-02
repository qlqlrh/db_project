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

import java.time.LocalDate;
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
    public String registerMeetingPage(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("meeting", new Meeting());
        return "registerMeeting";
    }

    @PostMapping("/register")
    public String registerMeeting(@ModelAttribute Meeting meeting, HttpSession session, Model model) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return "redirect:/login";
            }
            User currentUser = userService.getUserById(userId);
            meeting.setUser(currentUser);
            meetingService.registerMeeting(meeting);
            model.addAttribute("message", "모임이 성공적으로 등록되었습니다!");
            return "redirect:/meetings/list";
        } catch (Exception e) {
            model.addAttribute("error", "모임 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "registerMeeting";
        }
    }

    private User getCurrentUser() {
        // 현재 로그인한 사용자를 반환하는 메서드 (임시 구현)
        return new User("Default User", User.Role.Freshman, "default@example.com", "aaaaaa");
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
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.getUserById(userId);
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting == null || user == null) {
            model.addAttribute("error", "Invalid meeting or user.");
            return "redirect:/meetings/apply";
        }

        // 시간표와 충돌 확인
        if (timetableService.isConflictWithTimetable(userId, meeting.getDate(), meeting.getTime())) {
            model.addAttribute("error", "The meeting conflicts with your timetable.");
            return "redirect:/meetings/apply";
        }

        // 최대 인원 초과 확인
        if (meeting.getParticipantCount() >= meeting.getMaxParticipants()) {
            model.addAttribute("error", "The meeting is already full.");
            return "redirect:/meetings/apply";
        }

        // 모임 신청 처리
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setMeeting(meeting);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentService.saveEnrollment(enrollment);
        meeting.setParticipantCount(meeting.getParticipantCount() + 1);
        meetingService.saveMeeting(meeting);

        model.addAttribute("message", "Successfully applied for the meeting!");
        return "redirect:/meetings/apply";
    }

}
