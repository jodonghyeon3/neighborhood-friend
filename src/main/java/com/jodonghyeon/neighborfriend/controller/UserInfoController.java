package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.service.UserInfoService;
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
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<UserDto> userDetails(Authentication authentication) {
        return ResponseEntity.ok().body(userInfoService.getDetailUserInfo(authentication.getName()));
    }

    @PutMapping("/first-address")
    public ResponseEntity firstAddressAdd(Authentication authentication) throws UnknownHostException {

        userInfoService.addFirstAddress(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/second-address")
    public ResponseEntity secondAddressAdd(Authentication authentication) throws UnknownHostException {

        userInfoService.addSecondAddress(authentication.getName());
        return ResponseEntity.ok().build();
    }


}
