package com.Musicom.web_api_contract;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
public class BandDto {
    private Long id;

    private String url;

    @NotBlank(message = "Name must contain at least not space character")
    private String name;

    @Range(min = 0, max = 100, message = "Popularity must be between 0 and 100")
    private int popularity;

    private ImageDto image;

    private List<String> genres;

    private List<SummaryDto> tracks;
}
