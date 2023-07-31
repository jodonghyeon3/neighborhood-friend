package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.type.ParticipateStatus;
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
public class Participate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    private ParticipateStatus status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public static Participate from(User user, Post post) {
        return Participate.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .status(ParticipateStatus.WAIT)
                .post(post)
                .build();
    }

    public static Participate changeStatus(Participate participate, ParticipateStatus status) {
        return participate.builder()
                .status(status)
                .build();
    }
}
