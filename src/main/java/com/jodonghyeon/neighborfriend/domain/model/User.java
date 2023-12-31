package com.jodonghyeon.neighborfriend.domain.model;

import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.type.Gender;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime birth;
    private Long age;
    private Double rate;
    private Long ratePeople;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "first_address")),
            @AttributeOverride(name = "lat", column = @Column(name = "first_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "first_lon"))
    })
    private Address firstAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "second_address")),
            @AttributeOverride(name = "lat", column = @Column(name = "second_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "second_lon"))
    })
    private Address secondAddress;

    public static User from(SignUpForm form, String password) {
        return User.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(password)
                .name(form.getName())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .age(form.getAge())
                .gender(form.getGender())
                .rate(0.0)
                .ratePeople(0L)
                .build();
    }

}
