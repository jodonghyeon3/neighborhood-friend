package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.Model.User;
import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;

    public User signUp(SignUpForm form) {
        return userRepository.save(User.from(form));
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
