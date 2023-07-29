package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.model.Participate;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.ParticipateRepository;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromiseService {

    private ParticipateRepository participateRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    public String applyPromiseByPostId(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(id).orElseThrow
                (() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (PostStatus.RECRUITMENT_COMPLETE.equals(post.getStatus())) {
            new CustomException(ErrorCode.ALREADY_FINISHED_PROMISE);
        }

        participateRepository.save(Participate.from(user, post));
        return "약속 신청이 완료되었습니다.";
    }
}
