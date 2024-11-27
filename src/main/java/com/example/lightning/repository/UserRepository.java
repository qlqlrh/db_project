package com.example.lightning.repository;

import com.example.lightning.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User getUser() {
        User user = new User();
        user.setName("***");
        user.setStudentId("2022*****");
        user.setRole("**");
        user.setEmail("hong@example.com");
        return user;
    }
}
