package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public Optional<User> findByIdAndEmail(Long id, String email) {
        return userRepository.findById(id).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
