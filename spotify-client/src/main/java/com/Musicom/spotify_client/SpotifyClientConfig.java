package com.Musicom.spotify_client;

import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import com.Musicom.spotify_client.provider.TokenCodeProvider;
import com.Musicom.spotify_client.provider.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestClient;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:secret.properties")
})
public class SpotifyClientConfig {
    @Bean
    public SpotifyClientUriProvider uriBuilderProvider(
            @Value("${spotify.api.version}")
            String version,
            @Value("${spotify.api.host}")
            String host,
            @Value("${spotify.api.client_id}")
            String clientId,
            @Value("${spotify.api.client_secret}")
            String clientSecret) {
        return new SpotifyClientUriProvider(version, host, clientId, clientSecret);
    }

    @Bean
    public TokenCodeProvider tokenCodeProvider(
            @Value("${spotify.email}")
            String email,
            @Value("${spotify.password}")
            String password,
            SpotifyClientUriProvider
            uriProvider) {
        return new TokenCodeProvider(email, password, uriProvider);
    }

    @Bean
    public TokenProvider tokenProvider(
            @Value("${spotify.api.client_id}")
            String clientId,
            @Value("${spotify.api.client_secret}")
            String clientSecret,
            SpotifyClientUriProvider uriProvider,
            TokenCodeProvider tokenCodeProvider) {
        return new TokenProvider(clientId, clientSecret, RestClient.create(), uriProvider, tokenCodeProvider);
    }
}
