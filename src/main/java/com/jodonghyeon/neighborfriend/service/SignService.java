package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.form.SignInForm;
import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt}")
    private String key;

    private Long expireTimeMs = 1000 * 60 * 60L;

    public void signUp(SignUpForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            userRepository.save(User.from(form, encoder.encode(form.getPassword())));
        }
    }

    public String signIn(SignInForm form) {

        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        if (!encoder.matches(form.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = JwtTokenUtil.createToken(user.getEmail(), key, expireTimeMs);


        return token;
    }
}
