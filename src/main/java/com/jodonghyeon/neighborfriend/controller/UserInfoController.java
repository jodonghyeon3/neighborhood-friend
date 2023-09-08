package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/detail")
@Api(tags = "User Info API", description = "유저 정보 API")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping
    @ApiOperation(value = "유저 정보", notes = "유저정보를 가져옵니다.")
    public ResponseEntity<UserDto> userDetails(Authentication authentication) {
        return ResponseEntity.ok().body(userInfoService.getDetailUserInfo(authentication.getName()));
    }

    @PutMapping("/first-address")
    @ApiOperation(value = "첫번째 주소 등록", notes = "첫번째 주소를 등록합니다.")
    public ResponseEntity firstAddressAdd(Authentication authentication) throws UnknownHostException {

        userInfoService.addFirstAddress(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/second-address")
    @ApiOperation(value = "두번째 주소 등록", notes = "두번째 주소를 등록합니다.")
    public ResponseEntity secondAddressAdd(Authentication authentication) throws UnknownHostException {

        userInfoService.addSecondAddress(authentication.getName());
        return ResponseEntity.ok().build();
    }


}
