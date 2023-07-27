package com.jodonghyeon.neighborfriend.domain.repository;

import com.jodonghyeon.neighborfriend.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.homeAddress.address=:homeAddress")
    List<Post> findByAddress(String homeAddress);
}
