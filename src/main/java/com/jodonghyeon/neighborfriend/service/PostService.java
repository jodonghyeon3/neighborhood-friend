package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.PostDto;
import com.jodonghyeon.neighborfriend.domain.form.PostForm;
import com.jodonghyeon.neighborfriend.domain.model.Address;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.AddressType;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addAddressPost(PostForm form, String email, AddressType type) {
        User user = getUser(email);

        Address address = getAddress(type, user).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS)
        );

        postRepository.save(Post.from(form, user, address));
    }

    public List<PostDto> getListAddressPost(String email, AddressType type) {
        User user = getUser(email);

        Address address = getAddress(type, user).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS)
        );

        List<Post> posts = postRepository.findByAddress(address.getAddress());

        return posts.stream()
                .filter(post -> !post.getStatus().equals(PostStatus.RECRUITMENT_COMPLETE))
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    public void modifyPostStatus(Long postId, String email) {

        User user = getUser(email);
        Post post = getPost(postId);

        if (!post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Post.closePost(PostStatus.RECRUITMENT_COMPLETE);
    }

    public void modifyPost(String email, Long postsId, PostForm form) {
        User user = getUser(email);
        Post post = getPost(postsId);

        if (!post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Post.modify(form);
    }

    public void removePost(String email, Long postsId) {
        User user = getUser(email);
        Post post = getPost(postsId);

        if (!post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }
        postRepository.delete(post);
    }

    private Post getPost(Long postsId) {
        return postRepository.findById(postsId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
    }

    private Optional<Address> getAddress(AddressType type, User user) {
        if (type == AddressType.FIRSTADDRESS) {
            return Optional.ofNullable(user.getFirstAddress());
        } else {
            return Optional.ofNullable(user.getSecondAddress());
        }
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
