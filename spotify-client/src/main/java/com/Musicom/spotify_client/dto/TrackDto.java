package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TrackDto(String id,
                       @JsonProperty("duration_ms")
                       int durationMs,
                       @JsonProperty("external_urls")
                       UrlDto urlDto,
                       String name,
                       int popularity,
                       @JsonProperty("available_markets")
                       List<String> markets,
                       AlbumDto album,
                       @JsonProperty("artists")
                       List<BandIdDto> bandIdsDto) {

    public record BandIdDto(String id) {}
}