package com.example.lightning.controller;

import com.example.lightning.domain.Timetable;
import com.example.lightning.domain.User;
import com.example.lightning.service.TimetableService;
import com.example.lightning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showTimetableForm(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login"; // 로그인되지 않은 사용자라면 로그인 페이지로 이동
        }
        model.addAttribute("timetable", new Timetable());
        return "timetable";
    }

    @PostMapping("/create")
    public String createTimetable(@ModelAttribute Timetable timetable, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // User 객체를 데이터베이스에서 조회하여 Timetable에 설정
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/login"; // 유효하지 않은 사용자라면 로그인 페이지로 이동
        }

        // 시작 시간이 종료 시간보다 빠른지 확인
        if (timetable.getStartTime().isAfter(timetable.getEndTime())) {
            model.addAttribute("error", "시작 시간이 종료 시간보다 빨라야 합니다.");
            return "redirect:/timetable/create?error=invalid";
        }

        timetable.setUser(user); // User 객체를 Timetable에 설정
        timetableService.saveTimetable(timetable); // Timetable 저장

        model.addAttribute("message", "시간표가 성공적으로 등록되었습니다.");
        return "redirect:/timetable/create?success=true";
    }
}