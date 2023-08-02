package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String name;
    private String phone;
    private LocalDate birth;
    private Long age;
    private Double star_rating;
    private Gender gender;
    private Address homeAddress;
    private Address companyAddress;

    public static UserDto from(User form) {
        return UserDto.builder()
                .email(form.getEmail())
                .name(form.getName())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .age(form.getAge())
                .star_rating(form.getRate())
                .gender(form.getGender())
                .homeAddress(form.getFirstAddress())
                .companyAddress(form.getSecondAddress())
                .build();
    }

}
