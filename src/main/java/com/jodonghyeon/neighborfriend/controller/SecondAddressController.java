package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address/second/posts")
public class SecondAddressController {

    private final JwtAuthenticationProvider provider;
    private final PostService postService;

    /*
    1. 주소기반 컨트롤러 분리
    2. 약속 컨트롤러 따로
    3. 신청한 사람들의 테이블 따로 승인/거부 여부도 새로생긴 테이블
    4. 약속회원 테이블 약속회원 id PK키로 생성
    /home/posts/create-post
    /company/posts/create-post
     */


    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.companyPostRegister(form, vo.getEmail()));
    }



    @GetMapping("/lists")
    public ResponseEntity<List<PostDto>> postList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.companyPostList(vo.getEmail()));
    }

    @GetMapping("/close")
    public ResponseEntity<String> close(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.findByPostIdAndUserId(postId, vo.getId()));
    }







}
