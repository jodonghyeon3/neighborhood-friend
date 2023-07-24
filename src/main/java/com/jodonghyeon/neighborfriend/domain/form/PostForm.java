package com.jodonghyeon.neighborfriend.domain.form;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostForm {
    private String title;
    private String detail;
    private String place;
    private LocalDateTime time;

}
