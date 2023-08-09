package com.jodonghyeon.neighborfriend.domain.form;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentsForm {

    @NotEmpty
    private String comment;
}
