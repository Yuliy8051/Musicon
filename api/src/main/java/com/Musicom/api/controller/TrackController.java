package com.Musicom.api.controller;

import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.api.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("track")
public class TrackController {
    private final TrackService service;

    @GetMapping("all/{page}")
    public ResponseEntity<PagedTracksDto> getPage(@PathVariable int page) {
        return ResponseEntity.ok(service.getPage(page));
    }
}
