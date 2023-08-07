package com.jodonghyeon.neighborfriend.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignInForm {
    private String email;
    private String password;
}
