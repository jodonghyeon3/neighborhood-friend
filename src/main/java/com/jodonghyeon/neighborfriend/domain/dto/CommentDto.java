package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
    private Long id;
    private String userName;
    private String comment;

    public static CommentDto from(Comment form) {
        return CommentDto.builder()
                .id(form.getId())
                .userName(form.getUserName())
                .comment(form.getComment())
                .build();
    }
}
