package com.jodonghyeon.neighborfriend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressDto {
    private Double lat;
    private Double lon;
}
