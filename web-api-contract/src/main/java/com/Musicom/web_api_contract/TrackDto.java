package com.Musicom.web_api_contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TrackDto {
    private Long id;

    @JsonProperty("duration_ms")
    private int durationMs;

    private String url;

    private String name;

    private int popularity;

    private AlbumSummaryDto album;

    private Set<BandSummaryDto> bands;

    private long total;
}
