package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String homePostRegister(PostForm form, String email) {
        User user = userRepository.findByEmail(email).get();
        Double lat = user.getHomeAddress().getLat();
        Double lon = user.getHomeAddress().getLon();
        Post from = Post.from(form);
        from.setLon(lon);
        from.setLat(lat);
        from.setUser(user);
        postRepository.save(from);
        return "게시글이 등록되었습니다.";
    }


    public String companyPostRegister(PostForm form, String email) {
        User user = userRepository.findByEmail(email).get();
        Double lat = user.getCompanyAddress().getLat();
        Double lon = user.getCompanyAddress().getLon();
        Post from = Post.from(form);
        from.setLon(lon);
        from.setLat(lat);
        from.setUser(user);
        postRepository.save(from);
        return "게시글이 등록되었습니다.";
    }
}
