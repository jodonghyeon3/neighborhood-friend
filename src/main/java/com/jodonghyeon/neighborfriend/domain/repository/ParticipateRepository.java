package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Promise;
import com.jodonghyeon.neighborfriend.domain.type.ParticipateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipateRepository extends JpaRepository<Promise, Long> {
    List<Promise> findByPostId(Long PostId);

    Optional<Promise> findByUserEmail(String partiEmail);

    Optional<Promise> findByUserEmailAndUserNameAndStatus(String email, String name, ParticipateStatus participateStatus);

    Optional<Promise> findByUserEmailAndPostId(String promiseEmail, Long postId);
}

/*
Service 의 import 문을 보시면 Controller Layer 를 참조하고 있고, 이는 일반적인 Controller -> Service -> Repository 의 흐름을 역행하고 있다고 보시면 되요.

따라서, 리팩토링은 좋으나 AccountCreateDto 는 Controller layer 에서 생성하는 것이 맞겠습니다.
DTO라는 것 자체가 controller 레이어에 속함.


 */