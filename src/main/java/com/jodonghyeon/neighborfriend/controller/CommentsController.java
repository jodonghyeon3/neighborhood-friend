package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.CommentsDTO;
import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import com.jodonghyeon.neighborfriend.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{post}/comment")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("")
    public ResponseEntity commentAdd(Authentication authentication,
                                     @PathVariable(name = "post") Long postId,
                                     @RequestBody CommentsForm form) {
        commentsService.addComment(postId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CommentsDTO>> commentListGet(Authentication authentication,
                                                            @PathVariable(name = "post") Long postId) {
        return ResponseEntity.ok(commentsService.getListComment(authentication.getName(), postId));
    }

    @PutMapping("/{comment}")
    public ResponseEntity commentModify(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId,
                                        @RequestBody String form,
                                        @PathVariable(name = "post") Long postId) {
        commentsService.modifyComment(commentId, authentication.getName(), form, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{comment}")
    public ResponseEntity commentRemove(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId,
                                        @PathVariable(name = "post") Long postId) {
        commentsService.removeComment(authentication.getName(), commentId, postId);
        return ResponseEntity.ok().build();
    }
}
