package com.Musicom.spotify_client.provider;

import com.Musicom.spotify_client.dto.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

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

        return client
                .post()
                .uri(tokenUrl)
                .body(formData)
                .header("Authorization", "Basic " + getEncodeClientInfo())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .body(TokenDto.class)
                .token();
    }

    private String getEncodeClientInfo() {
        return Base64
                .getEncoder()
                .encodeToString((clientId+":"+clientSecret).getBytes());
    }
}
