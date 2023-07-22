package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.application.UserApplication;
import com.jodonghyeon.neighborfriend.domain.SignInForm;
import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserApplication userApplication;
    private final GeoService geoService;

    @PostMapping("/signup")
    public ResponseEntity<String> signInUser(@RequestBody SignUpForm form) throws UnknownHostException {
        return ResponseEntity.ok(userApplication.generalSignUp(form));
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signUpUser(@RequestBody SignInForm form) {
        return ResponseEntity.ok(userApplication.userLoginToken(form));
    }


}
