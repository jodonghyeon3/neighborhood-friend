package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.application.SignApplication;
import com.jodonghyeon.neighborfriend.domain.form.SignInForm;
import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignApplication signApplication;

    @PostMapping("/signup")
    public ResponseEntity signUpUser(@RequestBody SignUpForm form) throws UnknownHostException {
        return ResponseEntity.ok(signApplication.generalSignUp(form));
    }

    @PostMapping("/signin")
    public ResponseEntity signInUser(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signApplication.userLoginToken(form));
    }


}
