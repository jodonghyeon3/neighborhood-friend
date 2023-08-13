package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PromiseDTO;
import com.jodonghyeon.neighborfriend.domain.type.ParticipateStatus;
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
    public ResponseEntity promiseApply(Authentication authentication,
                                       @PathVariable(name = "postId") Long postId) {

        promiseService.applyPromise(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{postId}/promise")
    public ResponseEntity<List<PromiseDTO>> promiseUserListGet(Authentication authentication,
                                                               @PathVariable(name = "postId") Long postId) {

        return ResponseEntity.ok(promiseService.getPromiseListUser(authentication.getName(), postId));
    }

    @PutMapping("/{postId}/status")
    public ResponseEntity promiseModify(Authentication authentication,
                                        @PathVariable(name = "postId") Long postId,
                                        @RequestParam(name = "email") String userEmail,
                                        ParticipateStatus status) {

        promiseService.modifyPromise(authentication.getName(), userEmail, postId, status);

        return ResponseEntity.ok().build();
    }
}
