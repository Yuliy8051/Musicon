package com.Musicom.test;

import com.Musicom.updater.Updater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class TestController {
    private Updater updater;

    public TestController(Updater updater) {
        this.updater = updater;
    }

    @GetMapping("token")
    public ResponseEntity<Void> token() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("update")
    public ResponseEntity<Void> update() {
        updater.updateByDate(LocalDate.of(2024,4,16));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
