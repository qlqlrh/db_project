package com.example.lightning.service;

import com.example.lightning.domain.StudentRole;
import com.example.lightning.domain.User;
import com.example.lightning.repository.StudentRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentRoleService {
    @Autowired
    private StudentRoleRepository studentRoleRepository;

    public User.Role getRoleByStudentId(String studentId) {
        return studentRoleRepository.findByStudentId(studentId)
                .map(StudentRole::getRole) // Optional에서 역할(Role)을 추출
                .orElse(null); // 역할이 없으면 null 반환
    }
}
