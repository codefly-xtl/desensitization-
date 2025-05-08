package com.example.demo_test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping("/test01")
    public String test() {
        log.debug("xtl正在测试");
        return "test";
    }
}
