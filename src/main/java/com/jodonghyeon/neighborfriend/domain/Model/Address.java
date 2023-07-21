package com.jodonghyeon.neighborfriend.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String subdivisionName;
    private String cityName;
    private Double lat;
    private Double lon;

}
