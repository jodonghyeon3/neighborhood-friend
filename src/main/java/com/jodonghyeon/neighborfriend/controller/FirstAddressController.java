package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/first")
public class FirstAddressController {
    private final JwtAuthenticationProvider provider;
    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<String> postsAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.addFirstAddressPosts(form, vo.getEmail()));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> postsList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.listFirstAddressPosts(vo.getEmail()));
    }

    @PutMapping("/status")
    public ResponseEntity<String> postsClose(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.modifyPostsStatus(postId, vo.getId()));
    }

    @PutMapping("{posts}")
    public ResponseEntity<String> postsModify(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @PathVariable(name = "posts") Long postsId,
                                              @RequestBody PostForm form
    ) {

        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.modifyPosts(vo.getId(), postsId, form));
    }

    @DeleteMapping("{posts}")
    public ResponseEntity<String> postsRemove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @PathVariable(name = "posts") Long postsId
    ) {

        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.removePosts(vo.getId(), postsId));
    }


}
