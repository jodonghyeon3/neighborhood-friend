package com.jodonghyeon.neighborfriend.domain;

import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpForm {
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;
    private Gender gender;
    private Long age;
}
