package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AlbumDto(String id,
                       @JsonProperty("total_tracks")
                       int totalTracks,
                       @JsonProperty("external_urls")
                       UrlDto urlDto,
                       String name,
                       @JsonProperty("release_date")
                       String releaseDate,
                       List<ImageDto> images,
                       @JsonProperty("album_type")
                       String albumType,
                       @JsonProperty("available_markets")
                       List<String> markets) {
}
