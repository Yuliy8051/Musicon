package com.Musicom.web_api_contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {
    private Long id;

    @JsonProperty("duration_ms")
    private int durationMs;

    private String url;

    private String name;

    private int popularity;

    private SummaryDto album;

    private List<SummaryDto> bands;
}
