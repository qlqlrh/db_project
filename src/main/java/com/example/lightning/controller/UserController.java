package com.example.lightning.controller;

import com.example.lightning.domain.User;
import com.example.lightning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("message", "Registration successful!");
            return "login"; // 회원가입 성공 후 로그인 페이지로 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // 에러 발생 시 회원가입 페이지로 다시 이동
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            // 로그인 성공 시 세션에 userId 저장
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getName()); // 사용자 이름 저장 (옵션)
            return "redirect:/"; // 홈 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "index"; // index.html 파일로 이동
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // 로그인하지 않은 경우 login 페이지로 리다이렉트
        }

        // User 객체를 데이터베이스에서 가져오기
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/login"; // 유효하지 않은 사용자라면 로그인 페이지로 이동
        }

        model.addAttribute("user", user); // User 객체를 모델에 추가
        return "mypage"; // 로그인된 경우 mypage.html 렌더링
    }
}
