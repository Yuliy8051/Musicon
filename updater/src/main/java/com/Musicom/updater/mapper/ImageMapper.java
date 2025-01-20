package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Image;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.ImageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageMapper {
    private final MusicomDataCatalog db;

    public Image mapForBand(String id, ImageDto imageDto) {
        Image image = new Image();
        Band band = db.band().findBySourceId(id);
        image.setBand(band);
        image.setUrl(imageDto.url());
        image.setWidth(imageDto.width());
        image.setHeight(imageDto.height());
        return image;
    }

    public Image mapForAlbum(String id, ImageDto imageDto) {
        Image image = new Image();
        Album album = db.album().findBySourceId(id);
        image.setAlbum(album);
        image.setUrl(imageDto.url());
        image.setWidth(imageDto.width());
        image.setHeight(imageDto.height());
        return image;
    }
}
