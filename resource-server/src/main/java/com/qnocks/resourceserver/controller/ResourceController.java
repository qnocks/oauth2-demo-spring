package com.qnocks.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/messages")
    public String getMessage() {
        return "the protected message";
    }
}
