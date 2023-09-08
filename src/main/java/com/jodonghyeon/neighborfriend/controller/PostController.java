package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
@Api(tags = "Post API", description = "게시글 API")
public class PostController {
    private final PostService postService;

    @PostMapping
    @ApiOperation(value = "게시글 작성", notes = "사용자가 게시글을 작성합니다.")
    public ResponseEntity postAdd(Authentication authentication,
                                  @RequestBody PostForm form,
                                  @RequestParam(name = "address") AddressType type) {

        postService.addAddressPost(form, authentication.getName(), type);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation(value = "게시글 보기", notes = "사용자가 게시글을 조회합니다.")
    public ResponseEntity<List<PostDto>> postListGet(Authentication authentication,
                                                     @RequestParam(name = "address") AddressType type) {
        return ResponseEntity.ok(postService.getListAddressPost(authentication.getName(), type));
    }

    @PutMapping("/{postId}/status")
    @ApiOperation(value = "게시글 약속 마감", notes = "사용자가 작성한 약속 게시글을 마감합니다")
    public ResponseEntity postClose(Authentication authentication,
                                    @PathVariable(name = "postId") Long postId) {
        postService.modifyPostStatus(postId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{postId}")
    @ApiOperation(value = "게시글 수정", notes = "사용자가 게시글을 수정합니다.")
    public ResponseEntity postModify(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId,
                                     @RequestBody PostForm form) {

        postService.modifyPost(authentication.getName(), postId, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{postId}")
    @ApiOperation(value = "게시글 삭제", notes = "사용자가 게시글을 삭제합니다.")
    public ResponseEntity postRemove(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId) {

        postService.removePost(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }
}
