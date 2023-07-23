package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.UserDto;
import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import com.jodonghyeon.neighborfriend.geoLite2.GeoLocationDto;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import com.jodonghyeon.neighborfriend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/detail")
public class UserController {

    private final JwtAuthenticationProvider provider;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<UserDto> detailUser(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);

        User u = userService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(UserDto.from(u));
    }

    @GetMapping("/homeaddress")
    public ResponseEntity<String> setHomeAddress(@RequestHeader(name = "X-AUTH-TOKEN") String token) throws UnknownHostException {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(userService.findByIdAndEmailAndSetHomeAddress(vo.getId(), vo.getEmail()));
    }

    @GetMapping("/companyaddress")
    public ResponseEntity<String> setCompanyAddress(@RequestHeader(name = "X-AUTH-TOKEN") String token) throws UnknownHostException {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(userService.findByIdAndEmailAndSetCompanyAddress(vo.getId(), vo.getEmail()));
    }
}
