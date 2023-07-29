package com.jodonghyeon.neighborfriend.domain.dto;

import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String place;
    private LocalDateTime time;
    private PostStatus status;


    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .place(post.getPlace())
                .time(post.getTime())
                .status(post.getStatus())
                .build();
    }

}
