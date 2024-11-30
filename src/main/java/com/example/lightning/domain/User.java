package com.example.lightning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}) // 이메일에 대해 UNIQUE 제약 조건 추가
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "role", length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    // One-to-Many 관계로 Timetable 참조
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timetable> timetables = new ArrayList<>();

    // Default Constructor
    public User() {
    }

    // Constructor
    public User(String name, Role role, String email, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    // Enum for Role
    public enum Role {
        Select,
        Freshman,
        Senior_Student,
        Professor,
        Student_Council
    }
}
