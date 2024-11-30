package com.example.lightning.repository;

import com.example.lightning.domain.User;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // 이메일로 사용자 검색
=======
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
>>>>>>> a89d9c755d92be0f975b60d8f0343c3eea145979
}
