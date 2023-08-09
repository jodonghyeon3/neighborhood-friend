package com.jodonghyeon.neighborfriend.domain.form;

import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class SignUpForm {
    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private LocalDate birth;

    @NotEmpty
    @Min(9)
    private String phone;

    @NotEmpty
    private Gender gender;

    @NotEmpty
    private Long age;
}
