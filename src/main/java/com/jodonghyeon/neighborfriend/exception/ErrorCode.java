package com.jodonghyeon.neighborfriend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해 주세요."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치 하는 회원이 없습니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    ALREADY_FINISHED_PROMISE(HttpStatus.BAD_REQUEST, "이미 종료된 약속입니다."),
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "존재 하지 않는 게시글 입니다."),
    NOT_FOUND_ADDRESS(HttpStatus.BAD_REQUEST, "주소를 등록후 이용하세요."),
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다."),
    NOT_PERMITTED_CONNECT(HttpStatus.BAD_REQUEST,"접근 권한이 없습니다." );

    private final HttpStatus status;
    private final String detail;
}
