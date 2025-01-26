package com.Musicom.web.controller;

import com.Musicom.web.service.BandService;
import com.Musicom.web_api_contract.BandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("band")
@AllArgsConstructor
public class BandController {
    private BandService service;

    @GetMapping("all/{page}")
    public String getAll(Model model, @PathVariable int page) {
        model.addAttribute("pagedBands", service.getPage(page));
        return "band-page";
    }

    @GetMapping("search form")
    public String displaySearchForm(Model model) {
        model.addAttribute("band", new BandDto());
        return "band-search-form";
    }

    @GetMapping("search result")
    public String displaySearchResult(Model model, @ModelAttribute BandDto album) {
        model.addAttribute("bands", service.getByName(album.getName()));
        return "band-search-result";
    }
}
