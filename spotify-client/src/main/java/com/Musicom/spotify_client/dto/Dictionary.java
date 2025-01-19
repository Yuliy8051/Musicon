package com.Musicom.spotify_client.dto;

import java.util.List;

public class Dictionary {
    public record GenresDto(List<String> genres) {}

    public record MarketsDto(List<String> markets) {}
}
