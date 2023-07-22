package com.jodonghyeon.neighborfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class NeighborFriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeighborFriendApplication.class, args);

    }
}
