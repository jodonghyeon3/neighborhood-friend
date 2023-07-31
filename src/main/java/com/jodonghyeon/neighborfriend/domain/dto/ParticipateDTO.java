package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipateDTO {

    private String userEmail;

    private String userName;

    private Long age;

    private Gender gender;

    private Double rate;

//    private ParticipateStatus status;
    // 상태도 추가해야됨

    public static ParticipateDTO from(User user) {
        return ParticipateDTO.builder()
                .userEmail(user.getEmail())
                .userName(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .rate(user.getRate())
                .build();
    }
}