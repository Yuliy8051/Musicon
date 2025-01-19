package com.Musicom.spotify_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PagedResultDto(int limit,
                             int offset,
                             int total,
                             @JsonProperty("items")
                             List<TrackDto> tracks) {
}
