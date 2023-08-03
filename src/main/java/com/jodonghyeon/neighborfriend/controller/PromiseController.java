package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.dto.ParticipateDTO;
import com.jodonghyeon.neighborfriend.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PromiseController {

    private final JwtAuthenticationProvider provider;
    private final PromiseService promiseService;

    @PostMapping("{postId}/promise")
    public ResponseEntity promiseAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @PathVariable(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        promiseService.applyPromiseByPostId(vo.getEmail(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{postId}/promise")
    public ResponseEntity<List<ParticipateDTO>> promiseList(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                            @PathVariable(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.applyUserListByPostId(vo.getEmail(), postId));
    }

    @PutMapping("/promise/status")
    public ResponseEntity promiseApprove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestParam(name = "email") String email) {
        UserVo vo = provider.getUserVo(token);
        promiseService.approveUser(vo.getEmail(), email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/promise/cancel")
    public ResponseEntity promiseCancel(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                @RequestParam(name = "email") String email) {
        UserVo vo = provider.getUserVo(token);
        promiseService.cancelUser(vo.getEmail(), email);
        return ResponseEntity.ok().build();
    }


}
