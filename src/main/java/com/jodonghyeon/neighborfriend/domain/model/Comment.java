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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private String userName;

    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comment from(CommentsForm form, String name, Post post) {
        return Comment.builder()
                .comment(form.getComment())
                .userName(name)
                .post(post)
                .build();
    }

    public static Comment update(String form) {
        return Comment.builder()
                .comment(form)
                .build();
    }
}
