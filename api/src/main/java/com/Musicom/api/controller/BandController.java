package com.Musicom.api.controller;

import com.Musicom.api.service.BandService;
import com.Musicom.web_api_contract.BandDto;
import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("band")
@AllArgsConstructor
public class BandController {
    private BandService service;

    @GetMapping("page/{page}")
    public ResponseEntity<PagedBandsDto> getPage(@PathVariable int page) {
        return ResponseEntity.ok(service.getPage(page));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<BandDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    @PostMapping("add")
    public ResponseEntity<Void> add(@RequestBody BandDto band) {
        service.add(band);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
