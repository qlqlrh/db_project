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

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.role = user.getRole().name();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
