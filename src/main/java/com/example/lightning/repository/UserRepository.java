package com.example.lightning.repository;

import com.example.lightning.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // 이메일로 사용자 검색
    Optional<User> findByStudentId(String studentId); // 학번으로 사용자 검색

    Optional<User> findByStudentIdAndPassword(String studentId, String password);
}

