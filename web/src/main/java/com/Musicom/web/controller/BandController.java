package com.Musicom.web.controller;

import com.Musicom.web.service.BandService;
import com.Musicom.web_api_contract.BandDto;
import com.Musicom.web_api_contract.ImageDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("band")
@AllArgsConstructor
public class BandController {
    private BandService service;

    @GetMapping("all/{page}")
    public String displayAll(Model model, @PathVariable int page) {
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

    @GetMapping("add form")
    public String displayAddForm(Model model) {
        BandDto band = new BandDto();
        band.setGenres(new ArrayList<>());
        band.setImage(new ImageDto());
        model.addAttribute("band", band);
        return "band-add-form";
    }

    @PostMapping("add form")
    public String submitAddForm(@Valid @ModelAttribute BandDto band) {
        service.add(band);
        return "redirect:all/1";
    }
}
