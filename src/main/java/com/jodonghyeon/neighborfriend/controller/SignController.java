package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.form.SignInForm;
import com.jodonghyeon.neighborfriend.domain.form.SignUpForm;
import com.jodonghyeon.neighborfriend.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "Sign API", description = "회원가입/로그인 API")
public class SignController {

    private final SignService signService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "회원가입을 요청합니다.")
    public ResponseEntity userSignUp(@RequestBody SignUpForm form) {

        signService.signUp(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "로그인을 요청합니다.")
    public ResponseEntity<String> userSingIn(@RequestBody SignInForm form) {
        String token = signService.signIn(form);
        return ResponseEntity.ok().body(token);
    }
}
