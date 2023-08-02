package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Post extends BaseEntity {
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

    // 어느주소인지

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "address")),
            @AttributeOverride(name = "lat", column = @Column(name = "lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "lon"))
    })
    private Address address;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Post from(PostForm form, User userId, Address address1) {
        return Post.builder()
                .title(form.getTitle())
                .detail(form.getDetail())
                .place(form.getPlace())
                .time(form.getTime())
                .user(userId)
                .status(PostStatus.RECRUITING)
                .address(address1)
                .build();
    }

    public static Post closePost(PostStatus postStatus) {
        return Post.builder()
                .status(postStatus)
                .build();
    }

    public static Post modify(PostForm form) {
        return Post.builder()
                .title(form.getTitle())
                .detail(form.getDetail())
                .place(form.getPlace())
                .time(form.getTime())
                .build();
    }
}
