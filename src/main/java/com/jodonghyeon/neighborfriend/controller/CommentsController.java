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
@RequestMapping("/posts/{posts}/comments")
public class CommentsController {
    private final JwtAuthenticationProvider provider;
    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<String> commentsAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @PathVariable(name = "posts") Long postId,
                                              @RequestBody CommentsForm form) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(commentsService.addComments(postId, vo.getEmail(), form));
    }

    @GetMapping
    public ResponseEntity<List<CommentsDTO>> commentList(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                         @PathVariable(name = "posts") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(commentsService.listComments(vo.getEmail(), postId));
    }

    @PutMapping("{comment}")
    public ResponseEntity<String> commentModify(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                @PathVariable(name = "comment") Long commentId,
                                                @RequestBody String form,
                                                @PathVariable(name = "posts") Long postsId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(commentsService.modifyComments(commentId, vo.getEmail(), form));
    }

    @DeleteMapping("{comment}")
    public ResponseEntity<String> commentRemove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                @PathVariable(name = "comment") Long commentId,
                                                @PathVariable(name = "posts") Long postsId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(commentsService.removeComments(vo.getEmail(), vo.getId(), commentId));
    }
}
