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
@RequestMapping("/posts")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/{posts}/comments")
    public ResponseEntity commentsAdd(Authentication authentication,
                                      @PathVariable(name = "posts") Long postId,
                                      @RequestBody CommentsForm form) {
        commentsService.addComments(postId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{posts}/comments")
    public ResponseEntity<List<CommentsDTO>> commentList(Authentication authentication,
                                                         @PathVariable(name = "posts") Long postId) {
        return ResponseEntity.ok(commentsService.listComments(authentication.getName(), postId));
    }

    @PutMapping("/comments/{comment}")
    public ResponseEntity commentModify(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId,
                                        @RequestBody String form) {
        commentsService.modifyComments(commentId, authentication.getName(), form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{comment}")
    public ResponseEntity commentRemove(Authentication authentication,
                                        @PathVariable(name = "comment") Long commentId) {
        commentsService.removeComments(authentication.getName(), commentId);
        return ResponseEntity.ok().build();
    }
}
