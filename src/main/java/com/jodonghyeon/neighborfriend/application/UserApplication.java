package com.jodonghyeon.neighborfriend.application;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.SignInForm;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.jodonghyeon.neighborfriend.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class UserApplication {

    private final UserService userService;
    private final JwtAuthenticationProvider provider;


    public String generalSignUp(SignUpForm form) {

        if (userService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            User u = userService.signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }

    public String userLoginToken(SignInForm form) {
        User u = userService.validationUser(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        return provider.createToken(u.getEmail(), u.getId());
    }
}
