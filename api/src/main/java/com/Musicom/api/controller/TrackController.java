package com.Musicom.api.controller;

import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.api.service.TrackService;
import com.Musicom.web_api_contract.TrackDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("track")
public class TrackController {
    private final TrackService service;

    @GetMapping("page/{page}")
    public ResponseEntity<PagedTracksDto> getPage(@PathVariable int page) {
        return ResponseEntity.ok(service.getPage(page));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<TrackDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }
}
