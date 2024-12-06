package com.example.lightning.repository;

import com.example.lightning.domain.StudentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRoleRepository extends JpaRepository<StudentRole, String> {
    Optional<StudentRole> findByStudentId(String studentId);

     @Query("SELECT sr.role FROM StudentRole sr WHERE sr.studentId = :studentId")
    String findRoleByStudentId(@Param("studentId") String studentId);
}
