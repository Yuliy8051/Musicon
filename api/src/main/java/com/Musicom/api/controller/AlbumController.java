package com.Musicom.api.controller;

import com.Musicom.api.service.AlbumService;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("album")
@AllArgsConstructor
public class AlbumController {
    private AlbumService service;

    @GetMapping("page/{page}")
    public ResponseEntity<PagedAlbumsDto> getPage(@PathVariable int page) {
        return ResponseEntity.ok(service.getPage(page));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<AlbumDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }
}
