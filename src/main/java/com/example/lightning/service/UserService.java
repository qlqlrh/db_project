package com.example.lightning.service;

import com.example.lightning.domain.StudentRole;
import com.example.lightning.domain.User;
import com.example.lightning.repository.StudentRoleRepository;
import com.example.lightning.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRoleRepository studentRoleRepository;

    public User loginUserByStudentId(String studentId, String password) {
        // 학번과 비밀번호로 사용자 조회
        return userRepository.findByStudentIdAndPassword(studentId, password)
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public User updateUser(User user) {
        // 기존 사용자 데이터를 데이터베이스에서 조회
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user.getUserId()));

        // 수정된 값 설정
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        return existingUser;
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 학번 기반으로 StudentRole에서 역할 조회
        StudentRole studentRole = studentRoleRepository.findByStudentId(user.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found for studentId: " + user.getStudentId()));

        user.setRole(studentRole.getRole()); // 역할 설정
        return user;}

    public void registerUser(User user) {
        // 학번으로 역할 조회
        StudentRole studentRole = studentRoleRepository.findByStudentId(user.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        // 역할 설정
        user.setRole(studentRole.getRole());

        // 이메일 중복 확인
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }

        // 사용자 저장
        userRepository.save(user);
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Transactional // 트랜잭션 설정 추가
    public void updateUserAndRole(User user) {
        // student_role 테이블에 student_id가 존재하는지 확인
        boolean exists = studentRoleRepository.existsByStudentId(user.getStudentId());
        if (!exists) {
            throw new IllegalArgumentException("해당 student_id는 student_role 테이블에 존재하지 않습니다.");
        }

        // 사용자 업데이트
        userRepository.save(user);

        // student_role 테이블의 역할 업데이트
        studentRoleRepository.updateRoleByStudentId(user.getStudentId(), user.getRole());
    }

}