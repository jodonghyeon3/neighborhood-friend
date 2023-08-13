package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromiseDto {

    private String userEmail;

    private String userName;

    private Long age;

    private Gender gender;

    private Double rate;

    private PromiseStatus status;
    // 상태도 추가해야됨


    public static PromiseDto fromPromiseEntity(Promise promise) {
        return PromiseDto.builder()
                .status(promise.getStatus())
                .userName(promise.getUserName())
                .userEmail(promise.getUserEmail())
                .build();
    }

    public static PromiseDto fromUser(PromiseDto dto, User user) {
        return PromiseDto.builder()
                .age(user.getAge())
                .gender(user.getGender())
                .rate(user.getRate())
                .build();
    }
}
