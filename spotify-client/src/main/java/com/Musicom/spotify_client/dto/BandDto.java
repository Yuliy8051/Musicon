package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BandDto(String id,
                      @JsonProperty("external_urls")
                      UrlDto urlDto,
                      String name,
                      int popularity,
                      List<ImageDto> images,
                      List<String> genres) {
}
