package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostsService postsService;

    @PostMapping
    public ResponseEntity postsAdd(Authentication authentication,
                                   @RequestBody PostForm form,
                                   @RequestParam(name = "address") AddressType type) {

        postsService.addAddressPosts(form, authentication.getName(), type);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> postsList(Authentication authentication,
                                                   @RequestParam(name = "address") AddressType type) {
        return ResponseEntity.ok(postsService.listAddressPosts(authentication.getName(), type));
    }

    @PutMapping("/status")
    public ResponseEntity postsClose(Authentication authentication,
                                             @RequestParam(name = "postId") Long postId) {
        postsService.modifyPostsStatus(postId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{posts}")
    public ResponseEntity postsModify(Authentication authentication,
                                              @PathVariable(name = "posts") Long postsId,
                                              @RequestBody PostForm form) {

        postsService.modifyPosts(authentication.getName(), postsId, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{posts}")
    public ResponseEntity postsRemove(Authentication authentication,
                                              @PathVariable(name = "posts") Long postsId) {

        postsService.removePosts(authentication.getName(), postsId);
        return ResponseEntity.ok().build();
    }
}
