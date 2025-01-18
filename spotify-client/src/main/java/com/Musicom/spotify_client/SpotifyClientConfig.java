package com.Musicom.spotify_client;

import com.Musicom.spotify_client.client.SpotifyClientUriBuilderProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyClientConfig {
    @Bean
    public SpotifyClientUriBuilderProvider uriBuilderProvider(
            @Value("${spotify.api.version}")
            String version,
            @Value("${spotify.api.host}")
            String host) {
        return new SpotifyClientUriBuilderProvider(version, host);
    }
}
