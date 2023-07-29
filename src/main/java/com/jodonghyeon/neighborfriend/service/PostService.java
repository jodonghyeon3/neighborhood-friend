package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String createPost(PostForm form, String email) {
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

    public List<PostDto> findByUserAddress(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        String address = user.getHomeAddress().getAddress();

        return postRepository.findByAddress(address).stream()
                .map(PostDto::from).collect(Collectors.toList());
    }


    // 게시글 검색시 게시글 상태가 RECRUITMENT_COMPLETE일 경우에는 조회가 안되게
    public List<PostDto> companyPostList(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        String address = user.getCompanyAddress().getAddress();

        List<Post> posts = postRepository.findByAddress(address);
        List<PostDto> filteredPostDtos = posts.stream()
                .filter(post -> !post.getStatus().equals(PostStatus.RECRUITMENT_COMPLETE))
                .map(PostDto::from)
                .collect(Collectors.toList());

        return filteredPostDtos;
    }
}
