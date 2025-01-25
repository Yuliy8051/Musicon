package com.Musicom.web.controller;

import com.Musicom.web.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("album")
@AllArgsConstructor
public class AlbumController {
    private AlbumService albumService;

    @GetMapping("all/{page}")
    public String getAll(Model model, @PathVariable int page) {
        model.addAttribute("pagedAlbums", albumService.getPage(page));
        return "album-page";
    }
}
