package com.jodonghyeon.neighborfriend.domain.Model;

import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.domain.Type.Gender;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDate birth;
    private Long age;
    private Double star_rating;

    @Enumerated(EnumType.STRING)
    private Gender gender;



    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "subdivisionName", column = @Column(name = "home_subdivisonName")),
            @AttributeOverride(name = "cityName", column = @Column(name = "home_city")),
            @AttributeOverride(name = "lat", column = @Column(name = "home_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "home_lon"))
    })
    private Address homdAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "subdivisionName", column = @Column(name = "company_subdivisonName")),
            @AttributeOverride(name = "cityName", column = @Column(name = "company_city")),
            @AttributeOverride(name = "lat", column = @Column(name = "company_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "company_lon"))
    })
    private Address companyAddress;

    public static User from(SignUpForm form) {
        return User.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .age(form.getAge())
                .gender(form.getGender())
                .build();
    }
}
