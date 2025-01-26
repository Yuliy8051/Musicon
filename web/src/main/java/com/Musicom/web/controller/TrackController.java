package com.Musicom.web.controller;

import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.web.service.TrackService;
import com.Musicom.web_api_contract.TrackDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("track")
public class TrackController {
    private final TrackService service;

    @GetMapping("all/{page}")
    public String getAll(Model model, @PathVariable int page) {
        PagedTracksDto pagedTracks = service.getPage(page);
        model.addAttribute("pagedTracks", pagedTracks);
        return "all";
    }

    @GetMapping("search form")
    public String displaySearchForm(Model model) {
        model.addAttribute("track", new TrackDto());
        return "track-search-form";
    }

    @GetMapping("search result")
    public String displaySearchResult(Model model, @ModelAttribute TrackDto track) {
        model.addAttribute("tracks", service.getByName(track.getName()));
        return "track-search-result";
    }
}
