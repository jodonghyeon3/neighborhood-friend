package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.CommentsDTO;
import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import com.jodonghyeon.neighborfriend.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class CommentsController {
    private final JwtAuthenticationProvider provider;
    private final CommentsService commentsService;

    @PostMapping("/{posts}/comments")
    public ResponseEntity commentsAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                      @PathVariable(name = "posts") Long postId,
                                      @RequestBody CommentsForm form) {
        UserVo vo = provider.getUserVo(token);
        commentsService.addComments(postId, vo.getEmail(), form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{posts}/comments")
    public ResponseEntity<List<CommentsDTO>> commentList(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                         @PathVariable(name = "posts") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(commentsService.listComments(vo.getEmail(), postId));
    }

    @PutMapping("/comments/{comment}")
    public ResponseEntity commentModify(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @PathVariable(name = "comment") Long commentId,
                                        @RequestBody String form) {
        UserVo vo = provider.getUserVo(token);
        commentsService.modifyComments(commentId, vo.getEmail(), form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{comment}")
    public ResponseEntity commentRemove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @PathVariable(name = "comment") Long commentId) {
        UserVo vo = provider.getUserVo(token);
        commentsService.removeComments(vo.getEmail(), vo.getId(), commentId);
        return ResponseEntity.ok().build();
    }
}
