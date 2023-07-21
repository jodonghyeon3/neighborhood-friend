package com.jodonghyeon.neighborfriend.application;

import com.jodonghyeon.neighborfriend.domain.Model.User;
import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final SignUpService signUpService;

    public String generalSignUp(SignUpForm form) {

        if (signUpService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            User u = signUpService.signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }
}
