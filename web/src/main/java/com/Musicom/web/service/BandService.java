package com.Musicom.web.service;

import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class BandService {
    private RestClient client;

    public PagedBandsDto getPage(int page) {
        return client
                .get()
                .uri("band/page/" + page)
                .retrieve()
                .body(PagedBandsDto.class);
    }
}
