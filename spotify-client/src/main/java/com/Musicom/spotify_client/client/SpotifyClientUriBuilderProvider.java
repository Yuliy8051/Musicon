package com.Musicom.spotify_client.client;

import org.springframework.web.util.UriComponentsBuilder;

public record SpotifyClientUriBuilderProvider(String version,
                                              String host) {
    public UriComponentsBuilder builder() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host())
                .pathSegment(version());
    }
}
