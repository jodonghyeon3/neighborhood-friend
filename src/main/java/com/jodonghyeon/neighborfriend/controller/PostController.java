package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final JwtAuthenticationProvider provider;
    private final PostsService postsService;

    @PostMapping
    public ResponseEntity postsAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestBody PostForm form,
                                           @RequestParam(name = "address") AddressType type) {
        UserVo vo = provider.getUserVo(token);
        postsService.addAddressPosts(form, vo.getEmail(), type);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> postsList(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                   @RequestParam(name = "address") AddressType type) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.listAddressPosts(vo.getEmail(), type));
    }

    @PutMapping("/status")
    public ResponseEntity postsClose(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        postsService.modifyPostsStatus(postId, vo.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{posts}")
    public ResponseEntity postsModify(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @PathVariable(name = "posts") Long postsId,
                                              @RequestBody PostForm form) {

        UserVo vo = provider.getUserVo(token);
        postsService.modifyPosts(vo.getId(), postsId, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{posts}")
    public ResponseEntity postsRemove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @PathVariable(name = "posts") Long postsId) {

        UserVo vo = provider.getUserVo(token);
        postsService.removePosts(vo.getId(), postsId);
        return ResponseEntity.ok().build();
    }
}
