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
public class PostsService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // address 테이블 하나 만들어서 하는 방법도 생각
    public String addAddressPosts(PostForm form, String email, AddressType type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Address address = getAddress(type, user).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS)
        );

        postRepository.save(Post.from(form, user, address));
        return "게시글이 등록되었습니다.";
    }

    public List<PostDto> listAddressPosts(String email, AddressType type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Address address = getAddress(type, user).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS)
        );

        List<Post> posts = postRepository.findByAddress(address.getAddress())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        return posts.stream()
                .filter(post -> !post.getStatus().equals(PostStatus.RECRUITMENT_COMPLETE))
                .map(PostDto::from)
                .collect(Collectors.toList());
    }
    public String modifyPostsStatus(Long postId, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }
        Post.closePost(PostStatus.RECRUITMENT_COMPLETE);

        return "약속이 마감되었습니다.";
    }


    public String modifyPosts(Long userId, Long postsId, PostForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if (post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Post.modify(form);
        return "게시글이 수정되었습니다.";
    }

    public String removePosts(Long userId, Long postsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if (post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }
        postRepository.delete(post);
        return "게시글이 삭제되었습니다.";
    }

    private Optional<Address> getAddress(AddressType type, User user) {
        if (type == AddressType.FIRSTADDRESS) {
            return Optional.of(user.getFirstAddress());
        } else {
            return Optional.of(user.getSecondAddress());
        }
    }
}
