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
            @RequestParam("organizer") String organizer,
            @RequestParam("createFor") String createFor,
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
            if ("Freshman".equals(currentUser.getRole().name())) {
                model.addAttribute("error", "Freshman은 모임을 등록할 수 없습니다.");
                return "registerMeeting";
            }

            // Meeting 객체 생성 및 값 설정
            Meeting meeting = new Meeting();
            meeting.setUser(currentUser);
            meeting.setTitle(title);
            meeting.setSpace(location);
            meeting.setDate(LocalDate.parse(meetingDate));
            meeting.setTime(LocalTime.of(startHour, startMinute));
            meeting.setMaxParticipants(maxParticipants);
            meeting.setCreatedFor(createFor);

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
            return "redirect:/meetings/apply?error=invalid";
        }

        // 모임장이 자신이 만든 모임에 신청하려는 경우 제한
        if (meeting.getUser().getUserId().equals(userId)) {
            return "redirect:/meetings/apply?error=creator";
        }

        // Professor 예외 처리 (Professor는 모든 모임 신청 가능)
        if (!user.getRole().equals(User.Role.Professor)) {
            // 모임 대상 검증
            if (!meeting.getCreatedFor().equalsIgnoreCase(user.getRole().name())) {
                return "redirect:/meetings/apply?error=notApply";
            }

            // 최대 인원 초과 검증
            if (meeting.getParticipantCount() >= meeting.getMaxParticipants()) {
                return "redirect:/meetings/apply?full";
            }
        }

        // 모임 날짜로부터 요일 가져오기
        String dayOfWeekString = meeting.getDate().getDayOfWeek().toString();

        // 모임 시작 시간 가져오기
        LocalTime startTime = meeting.getTime();
        LocalTime endTime = startTime.plusHours(2); // 예시: 모임이 2시간이라고 가정

        // 중복 신청 확인
        if (enrollmentService.existsByUserAndMeeting(user, meeting)) {
            return "redirect:/meetings/apply?error=duplicate";
        }

        // 시간표 충돌 확인
        if (timetableService.isConflictWithTimetable(userId, dayOfWeekString, startTime, endTime)) {
            return "redirect:/meetings/apply?error=conflict";
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

        model.addAttribute("message", "모임 신청이 성공적으로 완료되었습니다!");
        return "redirect:/meetings/apply?success=true";
    }
}
