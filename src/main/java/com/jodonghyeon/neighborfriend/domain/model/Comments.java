package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private String userName;

    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comments from(CommentsForm form, String name, Post post) {
        return Comments.builder()
                .comment(form.getComment())
                .userName(name)
                .post(post)
                .build();
    }

    public static Comments update(String form) {
        return Comments.builder()
                .comment(form)
                .build();
    }
}
