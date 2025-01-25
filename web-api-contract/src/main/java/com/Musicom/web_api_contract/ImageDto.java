package com.Musicom.web_api_contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    private Long id;

    private String url;

    private int height;

    private int width;
}
