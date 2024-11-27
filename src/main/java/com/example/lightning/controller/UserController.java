package com.example.lightning.controller;

import com.example.lightning.domain.User;
import com.example.lightning.service.UserService;
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
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            model.addAttribute("name", user.getName());
            return "welcome"; // 로그인 성공 시 웰컴 페이지
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String welcomePage(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("message", "You have been logged out.");
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "index"; // index.html 파일로 이동
    }
}
