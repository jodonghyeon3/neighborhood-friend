package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.form.ReviewForm;
import com.jodonghyeon.neighborfriend.service.ReviewService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{postId}/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity reviewAdd(Authentication authentication,
                                    @PathVariable(name = "postId") Long postId,
                                    @RequestBody ReviewForm form) {

        reviewService.addReview(authentication.getName(), postId, form);
        return ResponseEntity.ok().build();
    }
}
