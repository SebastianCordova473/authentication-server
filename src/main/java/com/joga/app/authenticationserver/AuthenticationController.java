package com.joga.app.authenticationserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/login/oauth2")
    public ResponseEntity<String> callback(@RequestParam String code) {
        return ResponseEntity.ok("Code Received: " + code);
    }
}
