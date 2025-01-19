package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchDto(@JsonProperty("tracks") PagedResultDto result) {
}
