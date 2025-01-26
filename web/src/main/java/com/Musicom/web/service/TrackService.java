package com.Musicom.web.service;

import com.Musicom.web.exception.ApiException;
import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.web_api_contract.TrackDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@AllArgsConstructor
public class TrackService {
    private final RestClient client;

    public PagedTracksDto getPage(int page) {
        return client
                .get()
                .uri("track/page/" + page)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(PagedTracksDto.class);
    }

    public List<TrackDto> getByName(String name) {
        return client
                .get()
                .uri("track/name/" + name)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(new ParameterizedTypeReference<>(){});
    }
}
