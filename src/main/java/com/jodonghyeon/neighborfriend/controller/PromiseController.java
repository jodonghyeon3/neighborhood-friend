package com.jodonghyeon.neighborfriend.controller;

import com.jodonghyeon.neighborfriend.domain.dto.PromiseDto;
import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
import com.jodonghyeon.neighborfriend.service.PromiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/{postId}/promise")
@Api(tags = "Promise API", description = "약속 API")
public class PromiseController {
    private final PromiseService promiseService;

    @PostMapping("")
    @ApiOperation(value = "약속 요청", notes = "사용자가 게시글에 대한 약속을 요청합니다.")
    public ResponseEntity promiseRequest(Authentication authentication,
                                         @PathVariable(name = "postId") Long postId) {

        promiseService.requestPromise(authentication.getName(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    @ApiOperation(value = "약속 요청 유저 목록", notes = "게시글 작성자가 약속 요청한 유저들을 조회합니다.")
    public ResponseEntity<List<PromiseDto>> promiseUserListGet(Authentication authentication,
                                                               @PathVariable(name = "postId") Long postId) {

        return ResponseEntity.ok(promiseService.getPromiseListUser(authentication.getName(), postId));
    }

    @PutMapping("/status")
    @ApiOperation(value = "약속 승인", notes = "게시글 작성자가 약속 요청한 유저들을 승인/거부 합니다.")
    public ResponseEntity promiseStatusModify(Authentication authentication,
                                              @PathVariable(name = "postId") Long postId,
                                              @RequestParam(name = "email") String userEmail,
                                              PromiseStatus status) {

        promiseService.modifyPromiseStatus(authentication.getName(), userEmail, postId, status);

        return ResponseEntity.ok().build();
    }
}
