package com.Musicom.web.service;

import com.Musicom.web.exception.ApiException;
import com.Musicom.web_api_contract.PagedTracksDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class TrackService {
    private final RestClient restClient;

    public PagedTracksDto getPage(int page) {
        return restClient
                .get()
                .uri("track/page/" + page)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(PagedTracksDto.class);
    }
}
