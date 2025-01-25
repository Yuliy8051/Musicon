package com.Musicom.api.controller;

import com.Musicom.api.service.BandService;
import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("band")
@AllArgsConstructor
public class BandController {
    private BandService bandService;

    @GetMapping("page/{page}")
    public ResponseEntity<PagedBandsDto> getPage(@PathVariable int page) {
        return ResponseEntity.ok(bandService.getPage(page));
    }
}
