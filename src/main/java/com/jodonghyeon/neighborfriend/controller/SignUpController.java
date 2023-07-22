package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.application.SignUpApplication;
import com.jodonghyeon.neighborfriend.domain.SignUpForm;
import com.jodonghyeon.neighborfriend.geoLite2.GeoService;
import com.jodonghyeon.neighborfriend.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpApplication signUpApplication;
    private final GeoService geoService;

    @PostMapping()
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) throws UnknownHostException {
        return ResponseEntity.ok(signUpApplication.generalSignUp(form));
    }
}
