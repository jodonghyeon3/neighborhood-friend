package com.jodonghyeon.neighborfriend.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String detail;

    @NotEmpty
    private String place;

    @NotEmpty
    private LocalDateTime time;

}
