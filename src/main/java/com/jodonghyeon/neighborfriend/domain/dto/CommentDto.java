package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
