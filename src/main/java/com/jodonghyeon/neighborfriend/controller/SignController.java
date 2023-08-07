package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.form.SignInForm;
import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignController {

    private final SignService signService;

    @PostMapping("/signup")
    public ResponseEntity signUpUser(@RequestBody SignUpForm form) {

        signService.signUp(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody SignInForm form) {
        String token = signService.signIn(form);
        return ResponseEntity.ok().body(token);
    }
}
