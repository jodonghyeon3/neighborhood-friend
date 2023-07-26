package com.jodonghyeon.neighborfriend.geoLite2;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AddressDto {
    private String address;
    private Double lon;
    private Double lat;

}
