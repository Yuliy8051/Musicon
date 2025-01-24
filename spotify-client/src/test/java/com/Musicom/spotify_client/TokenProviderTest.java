package com.Musicom.spotify_client;

import com.Musicom.spotify_client.dto.TokenDto;
import com.Musicom.spotify_client.exception.SpotifyClientException;
import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import com.Musicom.spotify_client.provider.TokenCodeProvider;
import com.Musicom.spotify_client.provider.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TokenProviderTest {
    private String clientId;

    private String clientSecret;

    private RestClient client;

    private SpotifyClientUriProvider uriProvider;

    private TokenCodeProvider tokenCodeProvider;

    private TokenProvider tokenProvider;

    @BeforeEach
    public void setUp() {
        clientId = "testClientId";
        clientSecret = "testClientSecret";
        client = mock(RestClient.class);
        uriProvider = mock(SpotifyClientUriProvider.class);
        tokenCodeProvider = mock(TokenCodeProvider.class);
        tokenProvider = new TokenProvider(
                clientId,
                clientSecret,
                client,
                uriProvider,
                tokenCodeProvider
        );
    }

    @Test
    public void shouldReturnRefreshedTokenTest() {
        String codeValue = "codeValue";
        String tokenUrl = "tokenUrl";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", codeValue);
        formData.add("redirect_uri", "http://localhost:8081/token");
        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        TokenDto tokenDto = mock(TokenDto.class);
        String token = "valid-token";

        when(tokenCodeProvider.fetchCodeForToken()).thenReturn(codeValue);
        when(uriProvider.tokenUrl()).thenReturn(tokenUrl);
        when(client.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(tokenUrl)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(formData)).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(TokenDto.class)).thenReturn(tokenDto);
        when(tokenDto.token()).thenReturn(token);

        String result = tokenProvider.fetchRefreshedToken();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        assertEquals(token, result);
        verify(tokenCodeProvider).fetchCodeForToken();
        verify(uriProvider).tokenUrl();
        verify(client).post();
        verify(requestBodyUriSpec).uri(tokenUrl);
        verify(requestBodySpec).body(formData);
        verify(requestBodySpec, times(2)).header(captor.capture(), captor.capture());
        verify(requestBodySpec).retrieve();
        verify(responseSpec).body(TokenDto.class);
        verify(tokenDto).token();

        List<String> expectedValues = List.of(
                "Authorization",
                "Basic " + Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes()),
                "Content-Type",
                "application/x-www-form-urlencoded"
        );
        List<String> actualValues = captor.getAllValues();
        assertEquals(expectedValues, actualValues);
    }
    @Test
    public void shouldThrowTokenFetchingException() {
        String codeValue = "codeValue";
        String tokenUrl = "tokenUrl";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", codeValue);
        formData.add("redirect_uri", "http://localhost:8081/token");
        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(tokenCodeProvider.fetchCodeForToken()).thenReturn(codeValue);
        when(uriProvider.tokenUrl()).thenReturn(tokenUrl);
        when(client.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(tokenUrl)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(formData)).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(TokenDto.class)).thenReturn(null);

        assertThrows(SpotifyClientException.TokenFetchingException.class, () -> tokenProvider.fetchRefreshedToken());
    }
}
