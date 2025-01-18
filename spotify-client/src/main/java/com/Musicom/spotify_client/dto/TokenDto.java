package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDto(@JsonProperty("access_token") String token) {
}
