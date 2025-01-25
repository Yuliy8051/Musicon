package com.Musicom.web.controller;

import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.web.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @GetMapping("all/{page}")
    public String getAll(Model model, @PathVariable int page) {
        PagedTracksDto pagedTracks = trackService.getPage(page);
        model.addAttribute("pagedTracks", pagedTracks);
        return "all";
    }
}
