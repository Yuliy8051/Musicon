package com.Musicom.web.controller;

import com.Musicom.web.service.AlbumService;
import com.Musicom.web_api_contract.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("album")
@AllArgsConstructor
public class AlbumController {
    private AlbumService service;

    @GetMapping("all/{page}")
    public String displayAll(Model model, @PathVariable int page) {
        model.addAttribute("pagedAlbums", service.getPage(page));
        return "album-page";
    }

    @GetMapping("search form")
    public String displaySearchForm(Model model) {
        model.addAttribute("album", new AlbumDto());
        return "album-search-form";
    }

    @GetMapping("search result")
    public String displaySearchResult(Model model, @ModelAttribute AlbumDto album) {
        model.addAttribute("albums", service.getByName(album.getName()));
        return "album-search-result";
    }
}
