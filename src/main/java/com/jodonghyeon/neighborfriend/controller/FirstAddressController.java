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
@RequestMapping("/address/first/posts")
public class FirstAddressController {
    private final JwtAuthenticationProvider provider;
    private final PostService postService;
    @PostMapping("/register")
    public ResponseEntity<String> createPost(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.createPost(form, vo.getEmail()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto>> getPostList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.findByUserAddress(vo.getEmail()));
    }

    @GetMapping("/close")
    public ResponseEntity<String> close(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postService.findByPostIdAndUserId(postId, vo.getId()));
    }
}
