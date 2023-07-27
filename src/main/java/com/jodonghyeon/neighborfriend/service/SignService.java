package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;

    public User signUp(SignUpForm form) {
        return userRepository.save(User.from(form));
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> validationUser(String email, String password) {
        return userRepository.findByEmail(email).stream()
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }
}
