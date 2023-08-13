package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.CommentDto;
import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import com.jodonghyeon.neighborfriend.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{postId}/comment")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("")
    public ResponseEntity commentAdd(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId,
                                     @RequestBody CommentsForm form) {
        commentsService.addComment(postId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CommentDto>> commentListGet(Authentication authentication,
                                                           @PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(commentsService.getListComment(authentication.getName(), postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity commentModify(Authentication authentication,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @RequestBody String form,
                                        @PathVariable(name = "postId") Long postId) {
        commentsService.modifyComment(commentId, authentication.getName(), form, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity commentRemove(Authentication authentication,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @PathVariable(name = "postId") Long postId) {
        commentsService.removeComment(authentication.getName(), commentId, postId);
        return ResponseEntity.ok().build();
    }
}
