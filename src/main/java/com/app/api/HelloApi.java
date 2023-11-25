package com.app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloApi {
    @GetMapping("")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello! My name is an phuc!");
    }
}
