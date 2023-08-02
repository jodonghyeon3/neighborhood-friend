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
@RequestMapping("/posts/second")
public class SecondAddressController {

    private final JwtAuthenticationProvider provider;
    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<String> postAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody PostForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.addSecondAddressPosts(form, vo.getEmail()));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> postList(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.listSecondAddressPosts(vo.getEmail()));
    }

    @PutMapping("/status")
    public ResponseEntity<String> postClose(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(postsService.modifyPostsStatus(postId, vo.getId()));
    }

}
