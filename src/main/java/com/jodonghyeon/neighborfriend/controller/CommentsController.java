package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.CommentDto;
import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import com.jodonghyeon.neighborfriend.service.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{postId}/comment")
@Api(tags = "Comment API", description = "댓글 API")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("")
    @ApiOperation(value = "댓글 작성", notes = "사용자가 게시글에 대해 댓글을 남깁니다.")
    public ResponseEntity commentAdd(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId,
                                     @RequestBody CommentsForm form) {
        commentsService.addComment(postId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    @ApiOperation(value = "댓글 보기", notes = "사용자가 게시글의 댓글을 확인 합니다.")
    public ResponseEntity<List<CommentDto>> commentListGet(Authentication authentication,
                                                           @PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(commentsService.getListComment(authentication.getName(), postId));
    }

    @PutMapping("/{commentId}")
    @ApiOperation(value = "댓글 수정", notes = "사용자가 댓글을 수정합니다.")
    public ResponseEntity commentModify(Authentication authentication,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @RequestBody String form,
                                        @PathVariable(name = "postId") Long postId) {
        commentsService.modifyComment(commentId, authentication.getName(), form, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "사용자가 댓글을 삭제합니다.")
    public ResponseEntity commentRemove(Authentication authentication,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @PathVariable(name = "postId") Long postId) {
        commentsService.removeComment(authentication.getName(), commentId, postId);
        return ResponseEntity.ok().build();
    }
}
