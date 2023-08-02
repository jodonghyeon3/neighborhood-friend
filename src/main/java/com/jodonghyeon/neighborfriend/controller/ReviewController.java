package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.config.JwtAuthenticationProvider;
import com.jodonghyeon.neighborfriend.domain.common.UserVo;
import com.jodonghyeon.neighborfriend.domain.form.ReviewForm;
import com.jodonghyeon.neighborfriend.service.ReviewService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postsId}/review")
public class ReviewController {
    private final JwtAuthenticationProvider provider;
    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<String> reviewAdd(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @PathVariable(name = "postsId") Long postId,
                                            @RequestBody ReviewForm form) {
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(reviewService.addReview(vo.getId(), postId, form));
    }
}
