package com.example.lightning.controller;

import com.example.lightning.domain.Meeting;
import com.example.lightning.domain.User;
import com.example.lightning.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/register")
    public String registerMeetingPage(Model model) {
        // 사용자의 이름을 기본 모임장으로 설정
        User currentUser = getCurrentUser(); // 현재 로그인한 사용자 정보 가져오기
        model.addAttribute("user", currentUser);
        return "registerMeeting";
    }

    @PostMapping("/register")
    public String registerMeeting(@ModelAttribute Meeting meeting, Model model) {
        try {
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
}
