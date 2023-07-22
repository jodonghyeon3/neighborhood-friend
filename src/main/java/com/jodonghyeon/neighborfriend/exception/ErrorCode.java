package com.jodonghyeon.neighborfriend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 혹안해 주세요."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치 하는 회원이 없습니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다.");


    private final HttpStatus status;
    private final String detail;
}
