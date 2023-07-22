package com.jodonghyeon.neighborfriend.geoLite2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GeoLocationDto {
    private String subdivisionName;
    private String cityName;
    private Double latitude;
    private Double longitude;
}