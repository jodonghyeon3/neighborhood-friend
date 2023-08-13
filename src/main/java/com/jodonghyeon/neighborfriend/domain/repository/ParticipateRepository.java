package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.type.PromiseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipateRepository extends JpaRepository<Promise, Long> {
    List<Promise> findByPostId(Long PostId);

    Optional<Promise> findByUserEmail(String partiEmail);

    Optional<Promise> findByUserEmailAndUserNameAndStatus(String email, String name, PromiseStatus promiseStatus);

    Optional<Promise> findByUserEmailAndPostId(String promiseEmail, Long postId);
}

