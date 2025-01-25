package com.Musicom.web.service;

import com.Musicom.web.exception.ApiException;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class AlbumService {
    private final RestClient restClient;

    public PagedAlbumsDto getPage(int page) {
        return restClient
                .get()
                .uri("album/page/" + page)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(PagedAlbumsDto.class);
    }
}
