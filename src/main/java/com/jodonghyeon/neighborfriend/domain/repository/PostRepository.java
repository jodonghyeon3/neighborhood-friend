package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.firstAddress.address=:firstAddress")
    Optional<List<Post>> findByFirstAddress(String firstAddress);

    @Query("select p from Post p where p.user.secondAddress.address=:secondAddress")
    Optional<List<Post>> findBySecondAddress(String secondAddress);
    Optional<Post> findByIdAndUserId(Long postId, Long userId);
}
