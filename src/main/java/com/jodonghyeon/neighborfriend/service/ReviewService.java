package com.jodonghyeon.neighborfriend.service;

import com.jodonghyeon.neighborfriend.domain.form.ReviewForm;
import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.model.Post;
import com.jodonghyeon.neighborfriend.domain.model.Review;
import com.jodonghyeon.neighborfriend.domain.model.User;
import com.jodonghyeon.neighborfriend.domain.repository.ParticipateRepository;
import com.jodonghyeon.neighborfriend.domain.repository.PostRepository;
import com.jodonghyeon.neighborfriend.domain.repository.ReviewRepository;
import com.jodonghyeon.neighborfriend.domain.repository.UserRepository;
import com.jodonghyeon.neighborfriend.domain.type.ParticipateStatus;
import com.jodonghyeon.neighborfriend.domain.type.PostStatus;
import com.jodonghyeon.neighborfriend.exception.CustomException;
import com.jodonghyeon.neighborfriend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ParticipateRepository participateRepository;

    public void addReview(String email, Long postId, ReviewForm form) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_POST)
        );

        if (post.getStatus().equals(PostStatus.RECRUITING)) {
            throw new CustomException(ErrorCode.NOT_FINISHED_PROMISE);
        }

        List<Promise> promise = participateRepository.findByPostId(postId);

        participateRepository.findByUserEmailAndUserNameAndStatus(user.getEmail(), user.getName(), ParticipateStatus.APPROVE).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_PERMITTED_CONNECT)
        );

        reviewRepository.save(Review.from(form, user.getName(), user.getEmail()));

        User postsUser = post.getUser();
        Double rate = postsUser.getRate();


        if (rate.equals(0L)) {
            postsUser.setRate(form.getRating());
        } else {
            Long ratePeople = postsUser.getRatePeople();
            postsUser.setRate(((rate * ratePeople) + form.getRating()) / (ratePeople + 1));
            postsUser.setRatePeople(ratePeople + 1);
        }
    }
}
