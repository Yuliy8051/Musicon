package com.Musicom.spotify_client.provider;

import lombok.AllArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
public class SpotifyClientUriProvider {
    private final String version;
    private final String host;
    private final String clientId;

    public UriComponentsBuilder builderForSpotifyApi() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .pathSegment(version);
    }

    public String authorizationUrl() {
        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .pathSegment("accounts.spotify.com")
                .pathSegment("en")
                .pathSegment("authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", "http://localhost:8080/token")
                .build()
                .toUriString();
    }

    public String tokenUrl() {
        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("accounts.spotify.com")
                .pathSegment("api")
                .pathSegment("token")
                .toUriString();
    }
}
