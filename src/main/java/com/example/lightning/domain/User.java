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
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "studentId"}) // 이메일 및 학번에 UNIQUE 제약 조건 추가
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

//    @Column(name = "role", length = 50)
//    @Enumerated(EnumType.STRING)
//    private Role role;

    @Transient // 데이터베이스에 저장하지 않고 동적으로 계산
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private Role role;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "student_id", unique = true, nullable = false, length = 20)
    private String studentId; // 학번


    // One-to-Many 관계로 timetable 참조
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timetable> timetables = new ArrayList<>();

    // One-to-Many 관계로 meeting 참조
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> meetings = new ArrayList<>();

    // One-to-Many 관계로 enrollment 참조
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    // One-to-Many 관계로 review 참조
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Default Constructor
    public User() {
    }

    // Constructor
    public User(String name, String email, String password, String studentId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.studentId = studentId;
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
