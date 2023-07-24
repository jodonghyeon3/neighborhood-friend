package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String detail;
    private String place;
    private LocalDateTime time;

    private Double lat;
    private Double lon;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Post from(PostForm form) {
        return Post.builder()
                .title(form.getTitle())
                .detail(form.getDetail())
                .place(form.getPlace())
                .time(form.getTime())
                .build();
    }
}
