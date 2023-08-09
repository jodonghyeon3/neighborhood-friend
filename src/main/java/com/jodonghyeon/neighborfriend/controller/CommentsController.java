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
@RequestMapping("/post")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/{post}/comment")
    public ResponseEntity commentAdd(Authentication authentication,
                                     @PathVariable(name = "post") Long postId,
                                     @RequestBody CommentsForm form) {
        commentsService.addComment(postId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{post}/comment")
    public ResponseEntity<List<CommentsDTO>> commentListGet(Authentication authentication,
                                                            @PathVariable(name = "post") Long postId) {
        return ResponseEntity.ok(commentsService.getListComment(authentication.getName(), postId));
    }

    @PutMapping("/comment/{comment}")
    public ResponseEntity commentModify(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId,
                                        @RequestBody String form) {
        commentsService.modifyComment(commentId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{comment}")
    public ResponseEntity commentRemove(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId) {
        commentsService.removeComment(authentication.getName(), commentId);
        return ResponseEntity.ok().build();
    }
}
