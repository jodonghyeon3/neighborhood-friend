package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long id);
}
