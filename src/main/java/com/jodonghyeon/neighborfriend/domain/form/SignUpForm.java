package com.jodonghyeon.neighborfriend.domain.form;

import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class SignUpForm {
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;
    private Gender gender;
    private Long age;
}
