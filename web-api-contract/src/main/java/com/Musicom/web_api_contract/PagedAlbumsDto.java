package com.Musicom.web_api_contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedAlbumsDto {
    private int page;

    private long total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<AlbumDto> albums;
}
