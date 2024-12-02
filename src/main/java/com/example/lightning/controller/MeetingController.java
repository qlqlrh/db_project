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

    // 모임 등록 페이지
    @GetMapping("/register")
    public String registerMeetingPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "login";
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
            return "login";
        }

        model.addAttribute("user", user);
        return "registerMeeting";
    }

    // 모임 등록 처리
    @PostMapping("/register")
    public String registerMeeting(
            @RequestParam("title") String title,
            @RequestParam("location") String location,
            @RequestParam("meetingDate") String meetingDate,
            @RequestParam("startHour") int startHour,
            @RequestParam("startMinute") int startMinute,
            @RequestParam("maxParticipants") int maxParticipants,
            HttpSession session,
            Model model) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            //System.out.println("userId: " + userId);
            if (userId == null) {
                return "redirect:/login";
            }

            User currentUser = userService.getUserById(userId);
            //System.out.println("currentUser: " + currentUser);
            if (currentUser == null) {
                model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
                return "login";
            }

            // Meeting 객체 생성 및 값 설정
            Meeting meeting = new Meeting();
            //meeting.setUser(currentUser);
            meeting.setTitle(title);
            meeting.setSpace(location);
            meeting.setDate(LocalDate.parse(meetingDate));
            meeting.setTime(LocalTime.of(startHour, startMinute));
            meeting.setMaxParticipants(maxParticipants);
            //meeting.setUser(currentUser);

            // 중요: createdByRole 필드 설정
            meeting.setCreatedByRole(currentUser.getRole().name()); // 사용자 역할 기반 설정
            //System.out.println("Meeting: " + meeting);
            // 모임 등록
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
            return "redirect:/login";
        }

        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("meetings", meetings);
        return "meeting-apply";
    }

    // 모임 신청 처리
    @PostMapping("/apply/{meetingId}")
    public String applyForMeeting(@PathVariable Long meetingId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.getUserById(userId);
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting == null || user == null) {
            model.addAttribute("error", "유효하지 않은 모임 또는 사용자입니다.");
            return "redirect:/meetings/apply";
        }

        // 시간표 충돌 확인
        if (timetableService.isConflictWithTimetable(userId, meeting.getDate(), meeting.getTime())) {
            model.addAttribute("error", "모임 시간이 기존 시간표와 충돌합니다.");
            return "redirect:/meetings/apply";
        }

        // 최대 인원 초과 확인
        if (meeting.getParticipantCount() >= meeting.getMaxParticipants()) {
            model.addAttribute("error", "모임 정원이 초과되었습니다.");
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

        model.addAttribute("message", "모임 신청이 성공적으로 완료되었습니다!");
        return "redirect:/meetings/apply";
    }
}
