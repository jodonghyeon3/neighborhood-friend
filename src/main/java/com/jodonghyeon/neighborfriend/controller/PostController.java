package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity postAdd(Authentication authentication,
                                  @RequestBody PostForm form,
                                  @RequestParam(name = "address") AddressType type) {

        postService.addAddressPost(form, authentication.getName(), type);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> postListGet(Authentication authentication,
                                                     @RequestParam(name = "address") AddressType type) {
        return ResponseEntity.ok(postService.getListAddressPost(authentication.getName(), type));
    }

    @PutMapping("/{postId}/status")
    public ResponseEntity postClose(Authentication authentication,
                                    @PathVariable(name = "postId") Long postId) {
        postService.modifyPostStatus(postId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{postId}")
    public ResponseEntity postModify(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId,
                                     @RequestBody PostForm form) {

        postService.modifyPost(authentication.getName(), postId, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{postId}")
    public ResponseEntity postRemove(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId) {

        postService.removePost(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }
}
