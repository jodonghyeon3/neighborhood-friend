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
@RequestMapping("/address/promise")
public class PromiseController {

    private final JwtAuthenticationProvider provider;
    private final PromiseService promiseService;

    @PostMapping
    public ResponseEntity<String> promiseAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.applyPromiseByPostId(vo.getEmail(), postId));
    }

    @GetMapping
    public ResponseEntity<List<ParticipateDTO>> promiseList(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                            @RequestParam(name = "postId") Long postId) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.applyUserListByPostId(vo.getEmail(), postId));
    }

    @PutMapping("/status")
    public ResponseEntity<String> promiseApprove(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestParam(name = "email") String email) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.approveUser(vo.getEmail(), email));
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> promiseCancel(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                @RequestParam(name = "email") String email) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.cancelUser(vo.getEmail(), email));
    }


}
