package com.Musicom.web.service;

import com.Musicom.web.exception.ApiException;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {
    private final RestClient client;

    public PagedAlbumsDto getPage(int page) {
        return client
                .get()
                .uri("album/page/" + page)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(PagedAlbumsDto.class);
    }

    public List<AlbumDto> getByName(String name) {
        return client
                .get()
                .uri("album/name/" + name)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(new ParameterizedTypeReference<>(){});

    }
}
