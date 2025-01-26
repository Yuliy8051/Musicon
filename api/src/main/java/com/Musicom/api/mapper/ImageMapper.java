package com.Musicom.api.mapper;

import com.Musicom.api.exception.NotImageLinkException;
import com.Musicom.api.image_link_checker.ImageLinkChecker;
import com.Musicom.data.model.Image;
import com.Musicom.web_api_contract.ImageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("apiImageMapper")
@AllArgsConstructor
public class ImageMapper implements IMap<ImageDto, Image> {
    private final ImageLinkChecker imageLinkChecker;

    @Override
    public Image mapDto(ImageDto imageDto) {
        String url = imageDto.getUrl().trim();
        if (!imageLinkChecker.isImageLink(url))
            throw new NotImageLinkException(url);
        Image image = new Image();
        image.setHeight(640);
        image.setWidth(640);
        image.setUrl(url);
        return image;
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
