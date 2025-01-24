package com.Musicom.spotify_client;

import com.Musicom.spotify_client.client.SpotifyClient;
import com.Musicom.spotify_client.dto.*;
import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import com.Musicom.spotify_client.provider.WebDriverProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RestClientTest(SpotifyClient.class)
@ContextConfiguration(classes = {SpotifyClientConfig.class, WebDriverProvider.class})
public class SpotifyClientTest {
    private SpotifyClientUriProvider uriProvider;

    private RestClient.Builder builder;

    private MockServerRestClientCustomizer customizer;

    private String token;

    private SpotifyClient spotifyClient;

    @BeforeEach
    public void setUp() {
        builder = RestClient.builder();
        customizer = new MockServerRestClientCustomizer();
        customizer.customize(builder);
        uriProvider = mock(SpotifyClientUriProvider.class);
        token = "token";
        spotifyClient = new SpotifyClient(uriProvider, builder.build());
        spotifyClient.setToken(token);
    }

    @Test
    public void shouldReturnPageByDateWhenOffsetIsNotSpecified() {
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        LocalDate date = LocalDate.now();
        int limit = 50;
        int offset = 0;
        String url = "https://api.spotify.com/v1/search/?q=release_date:"
                + date + "&type=track&limit=" + limit + "&offset=" + offset;
        String json = "{\"tracks\": {\"limit\": "+ limit +", \"offset\": " + offset +
                ", \"total\": 456, \"items\": []}}";
        SearchDto expected = new SearchDto(new PagedResultDto(limit, offset,456, List.of()));

        when(uriProvider.builderForSpotifyApi()).thenReturn(uriBuilder);
        when(uriBuilder.pathSegment(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.queryParam(anyString(), any(Object.class))).thenReturn(uriBuilder);
        when(uriBuilder.toUriString()).thenReturn(url);

        customizer.getServer()
                .expect(MockRestRequestMatchers.header("Authorization", "Bearer " + token))
                .andExpect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

        SearchDto result = spotifyClient.fetchPageByDate(date);

        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        assertEquals(expected, result);

        verify(uriProvider).builderForSpotifyApi();
        verify(uriBuilder).pathSegment(stringCaptor.capture());
        verify(uriBuilder, times(2)).queryParam(stringCaptor.capture(), stringCaptor.capture());
        verify(uriBuilder, times(2)).queryParam(stringCaptor.capture(), integerCaptor.capture());
        verify(uriBuilder).toUriString();

        List<Integer> capturedIntegers = integerCaptor.getAllValues();
        List<String> capturedStrings = stringCaptor.getAllValues();

        List<Integer> expectedIntegers = List.of(limit, offset);
        List<String> expectedStrings = List.of(
                "search",
                "q",
                "release_date:" + date,
                "type",
                "track",
                "limit",
                "offset"
        );

        assertEquals(expectedIntegers, capturedIntegers);
        assertEquals(expectedStrings, capturedStrings);
    }
    @Test
    public void shouldReturnPageByDateWhenOffsetIsSpecified() {
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        LocalDate date = LocalDate.now();
        int limit = 50;
        int offset = 50;
        String url = "https://api.spotify.com/v1/search/?q=release_date:"
                + date + "&type=track&limit=" + limit + "&offset=" + offset;
        String json = "{\"tracks\": {\"limit\": "+ limit +", \"offset\": " + offset +
                ", \"total\": 456, \"items\": []}}";
        SearchDto expected = new SearchDto(new PagedResultDto(limit, offset,456, List.of()));

        when(uriProvider.builderForSpotifyApi()).thenReturn(uriBuilder);
        when(uriBuilder.pathSegment(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.queryParam(anyString(), any(Object.class))).thenReturn(uriBuilder);
        when(uriBuilder.toUriString()).thenReturn(url);

        customizer.getServer()
                .expect(MockRestRequestMatchers.header("Authorization", "Bearer " + token))
                .andExpect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

        SearchDto result = spotifyClient.fetchPageByDate(date, offset);

        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        assertEquals(expected, result);

        verify(uriProvider).builderForSpotifyApi();
        verify(uriBuilder).pathSegment(stringCaptor.capture());
        verify(uriBuilder, times(2)).queryParam(stringCaptor.capture(), stringCaptor.capture());
        verify(uriBuilder, times(2)).queryParam(stringCaptor.capture(), integerCaptor.capture());
        verify(uriBuilder).toUriString();

        List<Integer> capturedIntegers = integerCaptor.getAllValues();
        List<String> capturedStrings = stringCaptor.getAllValues();

        List<Integer> expectedIntegers = List.of(limit, offset);
        List<String> expectedStrings = List.of(
                "search",
                "q",
                "release_date:" + date,
                "type",
                "track",
                "limit",
                "offset"
        );

        assertEquals(expectedIntegers, capturedIntegers);
        assertEquals(expectedStrings, capturedStrings);
    }
    @Test
    public void shouldReturnBand() {
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        String id = "testUUID";
        String url = "https://api.spotify.com/v1/artists/" + id;
        String spotifyUrl = "testSpotifyUrl";
        String name = "testBandName";
        int popularity = 69;
        String json = "{\"id\": \"" + id + "\", \"external_urls\": " +
                "{\"spotify\": \"" + spotifyUrl + "\"}, \"name\": \"" + name + "\", " +
                "\"popularity\": " + popularity + ", \"images\": [], \"genres\": []}";
        BandDto expected = new BandDto(id, new UrlDto(spotifyUrl), name, popularity, List.of(), List.of());

        when(uriProvider.builderForSpotifyApi()).thenReturn(uriBuilder);
        when(uriBuilder.pathSegment(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.toUriString()).thenReturn(url);

        customizer.getServer()
                .expect(MockRestRequestMatchers.header("Authorization", "Bearer " + token))
                .andExpect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

        BandDto result = spotifyClient.fetchBand(id);

        assertEquals(expected, result);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(uriProvider).builderForSpotifyApi();
        verify(uriBuilder, times(2)).pathSegment(captor.capture());
        verify(uriBuilder).toUriString();

        List<String> capturedStrings = captor.getAllValues();
        List<String> expectedStrings = List.of("artists", id);
        assertEquals(expectedStrings, capturedStrings);
    }
    @Test
    public void shouldReturnMarkets() {
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        String url = "https://api.spotify.com/v1/markets";
        String json = "{\"markets\": [\"US\", \"PL\", \"UA\"]}";
        MarketsDto expected = new MarketsDto(List.of("US", "PL", "UA"));
        String pathSegment = "markets";

        when(uriProvider.builderForSpotifyApi()).thenReturn(uriBuilder);
        when(uriBuilder.pathSegment(pathSegment)).thenReturn(uriBuilder);
        when(uriBuilder.toUriString()).thenReturn(url);

        customizer.getServer()
                .expect(MockRestRequestMatchers.header("Authorization", "Bearer " + token))
                .andExpect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

        MarketsDto result = spotifyClient.fetchMarkets();

        assertEquals(expected, result);

        verify(uriProvider).builderForSpotifyApi();
        verify(uriBuilder).pathSegment(pathSegment);
        verify(uriBuilder).toUriString();
    }
}
