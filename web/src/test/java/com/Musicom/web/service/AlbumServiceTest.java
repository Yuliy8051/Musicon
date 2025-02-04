package com.Musicom.web.service;

import com.Musicom.web.WebConfig;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RestClientTest(AlbumService.class)
@ContextConfiguration(classes = WebConfig.class)
public class AlbumServiceTest {
    private RestClient.Builder builder;

    private MockServerRestClientCustomizer customizer;

    private AlbumService service;

    @BeforeEach
    public void setUp() {
        builder = RestClient.builder();
        customizer = new MockServerRestClientCustomizer();
        customizer.customize(builder);
        service = new AlbumService(builder.build());
    }

    @Test
    public void shouldReturnGetPage() {
        String url = "album/page/1";
        int page = 1;
        long total = 1;
        int totalPages = 1;
        List<AlbumDto> albumsDto = List.of();
        String json = "{\"page\": " + page + ", \"total\": " + total + "," +
        " \"total_pages\": " + totalPages + ", \"albums\": []}";

        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

        PagedAlbumsDto expected = new PagedAlbumsDto(page, total, totalPages, albumsDto);
        PagedAlbumsDto result = service.getPage(page);

        assertEquals(expected, result);
    }

}
