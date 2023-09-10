package com.jodonghyeon.neighborfriend.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
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
