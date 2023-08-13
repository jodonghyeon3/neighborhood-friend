package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
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
public class Promise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    private PromiseStatus status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public static Promise from(User user, Post post) {
        return Promise.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .status(PromiseStatus.WAIT)
                .post(post)
                .build();
    }

    public static Promise changeStatus(Promise promise, PromiseStatus status) {
        return promise.builder()
                .status(status)
                .build();
    }
}
