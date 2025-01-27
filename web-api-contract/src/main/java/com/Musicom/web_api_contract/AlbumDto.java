package com.Musicom.web_api_contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class AlbumDto {
    private long id;

    @JsonProperty("total_tracks")
    private int totalTracks;

    private String url;

    private String name;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    private ImageDto image;

    private List<SummaryDto> tracks;
}
