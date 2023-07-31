package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Long> {
    Optional<List<Participate>> findByPostId(Long PostId);

    Optional<Participate> findByUserEmail(String partiEmail);
}
