package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/detail")
public class UserInfoController {

    private final JwtAuthenticationProvider provider;
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<UserDto> userDetails(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);

        User u = userInfoService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(UserDto.from(u));
    }

    @PutMapping("/first-address")
    public ResponseEntity<String> firstAddressAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token) throws UnknownHostException {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(userInfoService.addFirstAddress(vo.getId(), vo.getEmail()));
    }

    @PutMapping("/second-address")
    public ResponseEntity<String> secondAddressAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token) throws UnknownHostException {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(userInfoService.addSecondAddress(vo.getId(), vo.getEmail()));
    }


}
