package com.Musicom.web.service;

import com.Musicom.web_api_contract.PagedTracksDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class TrackService {
    private final RestClient restClient;

    public PagedTracksDto getAll(int page) {
        return restClient
                .get()
                .uri("track/all/" + page)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
