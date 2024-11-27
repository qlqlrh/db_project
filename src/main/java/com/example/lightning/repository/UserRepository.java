package com.example.lightning.repository;

import com.example.lightning.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private User user = new User();

    public UserRepository() {
        user.setName("홍길동");
        user.setStudentId("20231234");
        user.setRole("팀장");
        user.setEmail("hong@example.com");
    }

    public User getUser() {
        return user;
    }

    public void updateUser(User updatedUser) {
        this.user.setName(updatedUser.getName());
        this.user.setStudentId(updatedUser.getStudentId());
        this.user.setRole(updatedUser.getRole());
        this.user.setEmail(updatedUser.getEmail());
    }
}
