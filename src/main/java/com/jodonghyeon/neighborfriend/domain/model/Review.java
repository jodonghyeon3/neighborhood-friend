package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.form.ReviewForm;
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
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String userEmail;

    private Double ratings;

    private String details;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public static Review from(ReviewForm form, String userName, String userEmail) {
        return Review.builder()
                .userName(userName)
                .userEmail(userName)
                .ratings(form.getRating())
                .details(form.getDetails())
                .build();
    }


}
