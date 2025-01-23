package com.Musicom.spotify_client;

import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotifyClientUriProviderTest {
    private String version;
    private String host;
    private String clientId;

    private SpotifyClientUriProvider uriProvider;

    @BeforeEach
    public void setUp() {
        version = "test-version";
        host = "test-host";
        clientId = "test-id";
        uriProvider = new SpotifyClientUriProvider(version, host, clientId);
    }

    @Test
    public void shouldReturnUriComponentsBuilder() {
        String expected = "https://" + host + "/" + version;

        String result = uriProvider
                .builderForSpotifyApi()
                .toUriString();

        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnAuthorizationUrl() {
        String expected = "https:/accounts.spotify.com/en/authorize?client_id="
                + clientId + "&response_type=code&redirect_uri=http://localhost:8081/token";

        String result = uriProvider.authorizationUrl();

        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnTokenUrl() {
        String expected = "https://accounts.spotify.com/api/token";

        String result = uriProvider.tokenUrl();

        assertEquals(expected, result);
    }
}
