package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.dto.CommentDto;
import com.jodonghyeon.neighborfriend.domain.form.CommentsForm;
import com.jodonghyeon.neighborfriend.domain.model.Comments;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.CommentsRepository;
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
public class CommentsService {

    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;

    private final PostRepository postRepository;

    public void addComment(Long postId, String email, CommentsForm form) {
        User user = getUser(email);

        Post post = getPost(postId);

        if (PostStatus.RECRUITMENT_COMPLETE.equals(post.getStatus())) {
            throw new CustomException(ErrorCode.ALREADY_FINISHED_PROMISE);
        }
        Comments from = Comments.from(form, user.getName(), post);
        commentsRepository.save(from);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public List<CommentDto> getListComment(String email, Long postId) {
        getUser(email);

        extracted(postId);

        return commentsRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT))
                .stream().map(CommentDto::from).collect(Collectors.toList());
    }

    private void extracted(Long postId) {
        getPost(postId);
    }

    public void modifyComment(Long commentId, String email, String form, Long postId) {
        getUser(email);

        extracted(postId);

        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (comments.getUserEmail().equals(email)) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        Comments.update(form);
    }

    public void removeComment(String email, Long commentId, Long postId) {
        User user = getUser(email);

        extracted(postId);

        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (!(user.getEmail().equals(comments.getUserEmail()) && user.getName().equals(user.getName()))) {
            throw new CustomException(ErrorCode.NOT_PERMITTED_CONNECT);
        }

        commentsRepository.delete(comments);
    }
}
