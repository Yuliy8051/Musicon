package com.Musicom.web_api_contract;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BandDto {
    private Long id;

    private String url;

    private String name;

    private int popularity;

    private ImageDto image;

    private List<String> genres;

    private List<SummaryDto> tracks;
}
