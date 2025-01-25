package com.Musicom.api.mapper;

import com.Musicom.data.model.Image;
import com.Musicom.web_api_contract.ImageDto;
import org.springframework.stereotype.Component;

@Component("apiImageMapper")
public class ImageMapper implements IMap<ImageDto, Image> {
    @Override
    public Image mapDto(ImageDto imageDto) { // TODO
        return null;
    }

    @Override
    public ImageDto mapEntity(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setUrl(image.getUrl());
        imageDto.setHeight(image.getHeight());
        imageDto.setWidth(image.getWidth());
        return imageDto;
    }
}
