package com.Musicom.spotify_client.provider;

import com.Musicom.spotify_client.dto.TokenDto;
import com.Musicom.spotify_client.exception.TokenFetchingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Slf4j
@AllArgsConstructor
public class TokenProvider {
    private final String clientId;

    private final String clientSecret;

    private final RestClient client;

    private final SpotifyClientUriProvider uriProvider;

    private TokenCodeProvider tokenCodeProvider;

    public String fetchRefreshedToken() {
        String codeValue = tokenCodeProvider.fetchCodeForToken();

        String tokenUrl = uriProvider.tokenUrl();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", codeValue);
        formData.add("redirect_uri", "http://localhost:8080/token");

        try {
            return client
                    .post()
                    .uri(tokenUrl)
                    .body(formData)
                    .header("Authorization", "Basic " + getEncodeClientInfo())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .retrieve()
                    .body(TokenDto.class)
                    .token();
        } catch (NullPointerException ex) {
            log.error("The value of token provided by Spotify API is null"); // TODO: move into exception handler
            throw new TokenFetchingException();
        }
    }

    private String getEncodeClientInfo() {
        return Base64
                .getEncoder()
                .encodeToString((clientId+":"+clientSecret).getBytes());
    }
}
