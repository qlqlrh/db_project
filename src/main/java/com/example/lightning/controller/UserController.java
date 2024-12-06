package com.example.lightning.controller;

import com.example.lightning.domain.*;
import com.example.lightning.repository.StudentRoleRepository;
import com.example.lightning.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StudentRoleService studentRoleService;

    @Autowired
    private StudentRoleRepository studentRoleRepository;

    // 메인 페이지 달력을 위한 모델 등록
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = userService.getUserById(userId);
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", user.getRole());
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        List<Meeting> meetings = meetingService.getAllMeetings();
        if (meetings == null) {
            meetings = new ArrayList<>();
        }
        // meetingsDTO: Meeting 객체를 MeetingDTO 객체로 변환
        List<MeetingDTO> meetingsDTO = meetings.stream().map(MeetingDTO::new).collect(Collectors.toList());
        // meetingsJson: meetingsDTO를 JSON 문자열로 변환
        String meetingsJson = "";
        try {
            meetingsJson = new ObjectMapper().writeValueAsString(meetingsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println("meetingsJson: " + meetingsJson);

        model.addAttribute("meetingsJson", meetingsJson);

        return "index";
    }


    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/signup?success=true"; // 회원가입 성공 후 로그인 페이지로 이동
        } catch (IllegalArgumentException e) {
            // 이메일 중복 처리
            model.addAttribute("error", e.getMessage());
            return "signup"; // 에러 발생 시 회원가입 페이지로 다시 이동
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String studentId, @RequestParam String password, HttpSession session, Model model) {
        // UserService에서 학번과 비밀번호로 사용자 검증
        User user = userService.loginUserByStudentId(studentId, password);
        if (user != null) {
            // 세션에 사용자 정보를 저장
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userRole", user.getRole());
            return "redirect:/"; // 로그인 성공 시 홈 페이지로 리다이렉트
        } else {
            // 로그인 실패 시 에러 메시지 추가
            model.addAttribute("error", "유효하지 않은 학번이나 비밀번호입니다!");
            return "login"; // 로그인 페이지로 다시 이동
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }

    // 마이페이지 띄움
    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login"; // 로그인하지 않은 경우 login 페이지로 리다이렉트
        }

        // User 객체를 데이터베이스에서 가져오기
        User user = userService.getUserById(userId);
        model.addAttribute("user", user); // User 객체를 모델에 추가

        // 시간표 가져오기
        List<Timetable> timetables = timetableService.getTimetablesByUserId(userId);
        model.addAttribute("timetables", timetables);

        // 생성한 모임 정보 가져오기
        List<Meeting> createdMeetings = meetingService.getMeetingsByCreator(userId);
        model.addAttribute("createdMeetings", createdMeetings);

        // 신청한 모임 정보 가져오기
        List<Meeting> enrolledMeetings = enrollmentService.getMeetingsByUserId(userId);
        model.addAttribute("enrolledMeetings", enrolledMeetings);

        // 후기를 작성한 사용자 정보 가져오기
        Map<Long, Boolean> reviewStatusMap = new HashMap<>();
        for (Meeting meeting : enrolledMeetings) {
            boolean hasReviewed = reviewService.hasUserReviewed(userId, meeting.getMeetingId());
            reviewStatusMap.put(meeting.getMeetingId(), hasReviewed);
        }
        model.addAttribute("reviewStatusMap", reviewStatusMap);

        return "mypage"; // 로그인된 경우 mypage.html 렌더링
    }
    // 회원관리
    @GetMapping("/manage")
    public String manageUsers(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login"; // 로그인하지 않은 경우
        }

        User user = userService.getUserById(userId);
        if (!"Student_Council".equals(user.getRole().name())) {
            return "redirect:/"; // 권한이 없는 경우 홈 페이지로 리다이렉트
        }

        // 모든 사용자 가져오기
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTO = users.stream().map(u -> {
            String role = studentRoleRepository.findRoleByStudentId(u.getStudentId());
            return new UserDTO(u, role);
        }).collect(Collectors.toList());


        String usersJson = "";
        try {
            usersJson = new ObjectMapper().writeValueAsString(usersDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("usersJson: " + usersJson);

        model.addAttribute("usersJson", usersJson);

        return "manage";
    }


    @PostMapping("/manage/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute User user, Model model) {
        try {
            // Fetch the existing user from the database
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                model.addAttribute("error", "User not found!");
                return "redirect:/manage";
            }

            // Update the user details
            existingUser.setName(user.getName());
            existingUser.setRole(user.getRole());
            existingUser.setEmail(user.getEmail());

            // Save the updated user
            userService.updateUser(existingUser);

            model.addAttribute("message", "User updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating user!");
            e.printStackTrace();
        }

        return "redirect:/manage";
    }

    @PostMapping("/mypage")
    public String updateUser(@ModelAttribute User user, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // 로그인하지 않은 경우
        }

        // 세션에서 가져온 userId를 설정
        user.setUserId(userId);

        try {
            User updatedUser = userService.updateUser(user); // 데이터베이스 업데이트
            if(updatedUser.getName() != null) {updatedUser.getName();}
            if(updatedUser.getEmail() != null) {updatedUser.getEmail();}
            if(updatedUser.getRole() != null) {updatedUser.getRole();}
            System.out.println(updatedUser.getName());
            System.out.println(updatedUser.getEmail());
            System.out.println(updatedUser.getRole());
        } catch (Exception e) {
            model.addAttribute("error", "Error updating profile!");
            System.out.println(e.getMessage());
        }

        model.addAttribute("user", user); // 업데이트된 사용자 정보를 다시 모델에 추가
        return "mypage"; // 수정 후 다시 마이페이지 렌더링
    }

    @PostMapping("/mypage/updatePassword")
    public String updatePassword(HttpSession session, @RequestParam String newPassword, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            userService.updatePassword(userId, newPassword);
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 중 문제가 발생했습니다.");
        }
        return "mypage";
    }
}
