package com.example.lightning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "student_role")
public class StudentRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "student_id", unique = true, nullable = false, length = 20)
    private String studentId; // 학번

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private User.Role role;

    // Default Constructor
    public StudentRole() {
    }

    // Constructor
    public StudentRole(String studentId, User.Role role) {
        this.studentId = studentId;
        this.role = role;
    }
}
