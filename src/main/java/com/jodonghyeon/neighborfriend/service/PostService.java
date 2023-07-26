package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String homePostRegister(PostForm form, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        postRepository.save(Post.from(form, user));
        return "게시글이 등록되었습니다.";

    }


    public String companyPostRegister(PostForm form, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        postRepository.save(Post.from(form, user));
        return "게시글이 등록되었습니다.";
    }
}
