package com.jodonghyeon.neighborfriend.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Api(tags = "Test API", description = "TEST API")
public class HelloController {
    @GetMapping
    public String hello() {
        return "04:00 test";
    }
}
