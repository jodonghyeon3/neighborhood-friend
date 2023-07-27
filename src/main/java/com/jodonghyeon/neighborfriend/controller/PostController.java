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
@RequestMapping("/post")
public class PostController {

    private final JwtAuthenticationProvider provider;
    private final PostService postService;

    @PostMapping("/homeregister")
    public ResponseEntity<String> homeRegistration(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.homePostRegister(form, vo.getEmail()));
    }

    @PostMapping("/companyregister")
    public ResponseEntity<String> companyRegistration(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.companyPostRegister(form, vo.getEmail()));
    }

    @GetMapping("/homelist")
    public ResponseEntity<List<PostDto>> homeList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.homePostList(vo.getEmail()));
    }

    @GetMapping("/companylist")
    public ResponseEntity<List<PostDto>> CompanyList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.homePostList(vo.getEmail()));
    }




}
