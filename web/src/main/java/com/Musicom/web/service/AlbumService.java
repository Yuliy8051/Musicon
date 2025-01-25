package com.Musicom.web.service;

import com.Musicom.web_api_contract.PagedAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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
                .body(new ParameterizedTypeReference<>(){});
    }
}
