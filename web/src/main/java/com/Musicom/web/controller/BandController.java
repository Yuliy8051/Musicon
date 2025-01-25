package com.Musicom.web.controller;

import com.Musicom.web.service.BandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("band")
@AllArgsConstructor
public class BandController {
    private BandService bandService;

    @GetMapping("all/{page}")
    public String getAll(Model model, @PathVariable int page) {
        model.addAttribute("pagedBands", bandService.getPage(page));
        return "band-page";
    }
}
