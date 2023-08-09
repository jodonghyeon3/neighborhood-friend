package com.jodonghyeon.neighborfriend.domain.form;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ReviewForm {
    @NotEmpty
    private Double rating;
    @NotEmpty
    private String details;
}
