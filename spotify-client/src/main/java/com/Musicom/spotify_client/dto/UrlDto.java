package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UrlDto(@JsonProperty("spotify") String url) {
}
