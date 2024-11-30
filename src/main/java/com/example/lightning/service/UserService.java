package com.example.lightning.service;

import com.example.lightning.domain.User;
import com.example.lightning.repository.UserRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> a89d9c755d92be0f975b60d8f0343c3eea145979
import org.springframework.stereotype.Service;

@Service
public class UserService {
<<<<<<< HEAD

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
=======
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        return userRepository.getUser();
    }

    public void saveUser(User user) {
        // Repository에서 업데이트 메서드를 구현했다고 가정
        userRepository.updateUser(user);
    }
}
>>>>>>> a89d9c755d92be0f975b60d8f0343c3eea145979
