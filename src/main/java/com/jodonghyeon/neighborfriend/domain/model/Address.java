package com.jodonghyeon.neighborfriend.domain.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String address;
    private Double lat;
    private Double lon;

}
