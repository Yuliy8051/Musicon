package com.Musicom.api.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Image;
import com.Musicom.data.model.Track;
import com.Musicom.web_api_contract.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("apiAlbumMapper")
@AllArgsConstructor
public class AlbumMapper implements IMap <AlbumDto, Album> {
    private final ImageMapper imageMapper;

    private final SummaryMapper<Track> trackSummaryMapper;

    public List<AlbumDto> mapAllEntities(List<Album> albums) {
        return albums
                .stream()
                .map(this::mapEntity)
                .toList();
    }

    @Override
    public Album mapDto(AlbumDto albumDto) { // TODO
        return null;
    }

    @Override
    public AlbumDto mapEntity(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setId(album.getId());
        albumDto.setName(album.getName());
        albumDto.setUrl(album.getUrl());
        albumDto.setReleaseDate(album.getReleaseDate());
        albumDto.setTotalTracks(album.getTotalTracks());

        albumDto.setTracks(trackSummaryMapper.mapAllEntities(album.getTracks()));

        album.getImages()
                .stream()
                .max(Comparator.comparing(Image::getHeight))
                .ifPresent(image -> albumDto.setImage(imageMapper.mapEntity(image)));

        return albumDto;
    }
}
