package com.Musicom.web.service;

import com.Musicom.web.exception.ApiException;
import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class BandService {
    private final RestClient client;

    public PagedBandsDto getPage(int page) {
        return client
                .get()
                .uri("band/page/" + page)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new ApiException(new String(response.getBody().readAllBytes()));
                })
                .body(PagedBandsDto.class);
    }
}
