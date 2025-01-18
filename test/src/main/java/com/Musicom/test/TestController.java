package com.Musicom.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("token")
    public ResponseEntity<Void> token() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
