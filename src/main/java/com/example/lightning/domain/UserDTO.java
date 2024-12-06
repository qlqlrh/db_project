package com.example.lightning.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String password;
    private String studentId;

    public UserDTO(User user, String role) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.role = role; // 동적으로 가져온 역할 설정
    }
}
