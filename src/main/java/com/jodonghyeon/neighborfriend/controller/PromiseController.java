package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class PromiseController {

    private final JwtAuthenticationProvider provider;
    private final PromiseService promiseService;
    @GetMapping("/apply")
    public ResponseEntity<String> applyPromise(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestParam Long id) {
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(promiseService.applyPromiseByPostId(vo.getEmail(),id));
    }
}
