package com.Musicom.spotify_client.client;

import com.Musicom.spotify_client.dto.BandDto;
import com.Musicom.spotify_client.dto.MarketsDto;
import com.Musicom.spotify_client.dto.SearchDto;
import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter
public class SpotifyClient {
    private final SpotifyClientUriProvider uriProvider;

    private final RestClient restClient;

    private String token;

    public SearchDto fetchPageByDate(LocalDate date) {
        return fetchPageByDate(date, null);
    }

    public SearchDto fetchPageByDate(LocalDate date, Integer offset) {
        UriComponentsBuilder builder = uriProvider
                .builderForSpotifyApi()
                .pathSegment("search")
                .queryParam("q", "release_date:" + date)
                .queryParam("type", "track")
                .queryParam("limit", 50);

        if (offset == null)
            offset = 0;
        builder.queryParam("offset", offset);

        String url = builder.toUriString();

        return fetchResponseSpec(url).body(SearchDto.class);
    }

    public BandDto fetchBand(String id) {
        String url = uriProvider
                .builderForSpotifyApi()
                .pathSegment("artists")
                .pathSegment(id)
                .toUriString();
        return fetchResponseSpec(url).body(BandDto.class);
    }

    public MarketsDto fetchMarkets() {
        String url = uriProvider
                .builderForSpotifyApi()
                .pathSegment("markets")
                .toUriString();
        return fetchResponseSpec(url).body(MarketsDto.class);
    }

    private RestClient.ResponseSpec fetchResponseSpec(String url) {
        return restClient
                .get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve();
    }
}
