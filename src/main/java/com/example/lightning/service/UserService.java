package com.example.lightning.service;

import com.example.lightning.domain.User;
import com.example.lightning.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
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
