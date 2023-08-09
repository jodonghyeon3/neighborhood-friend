package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.ParticipateDTO;
import com.jodonghyeon.neighborfriend.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping("{postId}/promise")
    public ResponseEntity promiseAdd(Authentication authentication,
                                     @PathVariable(name = "postId") Long postId) {
        promiseService.applyPromiseByPostId(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{postId}/promise")
    public ResponseEntity<List<ParticipateDTO>> promiseList(Authentication authentication,
                                                            @PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(promiseService.applyUserListByPostId(authentication.getName(), postId));
    }

    @PutMapping("/promise/status")
    public ResponseEntity promiseApprove(Authentication authentication,
                                                 @RequestParam(name = "email") String email) {
        promiseService.approveUser(authentication.getName(), email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/promise/cancel")
    public ResponseEntity promiseCancel(Authentication authentication,
                                                @RequestParam(name = "email") String email) {
        promiseService.cancelUser(authentication.getName(), email);
        return ResponseEntity.ok().build();
    }


}
