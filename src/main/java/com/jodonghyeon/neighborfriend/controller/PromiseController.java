package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PromiseDto;
import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
import com.jodonghyeon.neighborfriend.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{postId}/promise")
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping("")
    public ResponseEntity promiseRequest(Authentication authentication,
                                         @PathVariable(name = "postId") Long postId) {

        promiseService.requestPromise(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<PromiseDto>> promiseUserListGet(Authentication authentication,
                                                               @PathVariable(name = "postId") Long postId) {

        return ResponseEntity.ok(promiseService.getPromiseListUser(authentication.getName(), postId));
    }

    @PutMapping("/status")
    public ResponseEntity promiseStatusModify(Authentication authentication,
                                              @PathVariable(name = "postId") Long postId,
                                              @RequestParam(name = "email") String userEmail,
                                              PromiseStatus status) {

        promiseService.modifyPromiseStatus(authentication.getName(), userEmail, postId, status);

        return ResponseEntity.ok().build();
    }
}
