package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.PromiseDto;
import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.ParticipateRepository;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class PromiseService {

    private final ParticipateRepository promiseRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void requestPromise(String email, Long id) {

        User user = getUser(email);

        Post post = postRepository.findByIdAndStatus(id, "RECRUITMENT_COMPLETE").orElseThrow
                (() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 게시글이 회원의 주소와 관련이 없을 경우
        if (!post.getAddress().getAddress().equals(user.getFirstAddress().getAddress())
                 && !post.getAddress().getAddress().equals(user.getSecondAddress().getAddress())) {
            throw new CustomException(ErrorCode.NOT_YOUR_ADDRESS_POST);
        }

        promiseRepository.save(Promise.from(user, post));
    }

    public List<PromiseDto> getPromiseListUser(String email, Long postId) {
        User user = getUser(email);

        Post post = postRepository.findByIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_PERMITTED_CONNECT));

        if (!post.getAddress().getAddress().equals(user.getFirstAddress().getAddress())
                && !post.getAddress().getAddress().equals(user.getSecondAddress().getAddress())) {
            throw new CustomException(ErrorCode.NOT_YOUR_ADDRESS_POST);
        }

        List<Promise> promiseUserList = promiseRepository.findByPostId(post.getId()).stream()
                .filter(promise -> !promise.getStatus().equals(PromiseStatus.CANCEL))
                .collect(Collectors.toList());

        List<User> users = promiseUserList.stream()
                .map(promise -> userRepository.findByEmail(promise.getUserEmail())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
                .collect(Collectors.toList());

        List<PromiseDto> collect = IntStream.range(0, promiseUserList.size())
                .mapToObj(i -> PromiseDto.fromUser(
                        PromiseDto.fromPromiseEntity(promiseUserList.get(i)),
                        users.get(i)))
                .collect(Collectors.toList());

        return collect;

    }

    public void modifyPromiseStatus(String userEmail, String promiseEmail, Long postId, PromiseStatus status) {
        User user = getUser(userEmail);

        Promise promise = promiseRepository.findByUserEmailAndPostId(promiseEmail, postId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_PROMISE)
        );

        if (!promise.getPost().getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Promise.changeStatus(promise, status);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
