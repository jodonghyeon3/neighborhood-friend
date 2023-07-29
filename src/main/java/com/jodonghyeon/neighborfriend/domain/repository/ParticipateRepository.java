package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Long> {
}
