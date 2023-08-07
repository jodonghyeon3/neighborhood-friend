package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.ParticipateDTO;
import com.jodonghyeon.neighborfriend.domain.model.Participate;
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

    private final ParticipateRepository participateRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void applyPromiseByPostId(String email, Long id) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(id).orElseThrow
                (() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (PostStatus.RECRUITMENT_COMPLETE.equals(post.getStatus())) {
            throw new CustomException(ErrorCode.ALREADY_FINISHED_PROMISE);
        }

        participateRepository.save(Participate.from(user, post));
    }

    public List<ParticipateDTO> applyUserListByPostId(String email, Long emailId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findByIdAndUserId(emailId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_PERMITTED_CONNECT));


        List<Participate> byPostId = participateRepository.findByPostId(post.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        List<User> users = new ArrayList<>();
        for (int i = 0; i < byPostId.size(); i++) {
            users.add(userRepository.findByEmail(byPostId.get(i).getUserEmail()).get());
        }
        return users.stream()
                .map(ParticipateDTO::from)
                .collect(Collectors.toList());

    }

    public void approveUser(String userEmail, String partiEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Participate participate = participateRepository.findByUserEmail(partiEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!participate.getPost().getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Participate.changeStatus(participate, ParticipateStatus.APPROVE);
    }

    public void cancelUser(String userEmail, String partiEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Participate participate = participateRepository.findByUserEmail(partiEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!participate.getPost().getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Participate.changeStatus(participate, ParticipateStatus.CANCEL);
    }
}
