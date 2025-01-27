package com.Musicom.web_api_contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PagedBandsDto {
    private int page;

    private long total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<BandDto> bands;
}
