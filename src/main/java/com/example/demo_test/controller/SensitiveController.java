package com.example.demo_test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class SensitiveController {
    @RequestMapping("/test01")
    public String test(String content) {

        return "test";
    }
}
