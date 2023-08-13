package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.PromiseDTO;
import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.ParticipateRepository;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.ParticipateStatus;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PromiseService {

    private final ParticipateRepository promiseRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void applyPromise(String email, Long id) {

        User user = getUser(email);

        Post post = postRepository.findById(id).orElseThrow
                (() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (PostStatus.RECRUITMENT_COMPLETE.equals(post.getStatus())) {
            throw new CustomException(ErrorCode.ALREADY_FINISHED_PROMISE);
        }

        promiseRepository.save(Promise.from(user, post));
    }

    public List<PromiseDTO> getPromiseListUser(String email, Long postId) {
        User user = getUser(email);

        Post post = postRepository.findByIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_PERMITTED_CONNECT));


        List<Promise> byPostId = promiseRepository.findByPostId(post.getId());

        List<User> users = new ArrayList<>();

        for (int i = 0; i < byPostId.size(); i++) {
            users.add(userRepository.findByEmail(byPostId.get(i).getUserEmail()).get());
        }

        return users.stream()
                .map(PromiseDTO::from)
                .collect(Collectors.toList());

    }

    public void modifyPromise(String userEmail, String promiseEmail, Long postId, ParticipateStatus status) {
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
