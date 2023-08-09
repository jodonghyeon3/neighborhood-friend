package com.jodonghyeon.neighborfriend.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@Builder
public class SignInForm {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
