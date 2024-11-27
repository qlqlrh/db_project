package com.example.lightning.controller;

import com.example.lightning.domain.User;
import com.example.lightning.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/mypage")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/mypage";
    }
}
