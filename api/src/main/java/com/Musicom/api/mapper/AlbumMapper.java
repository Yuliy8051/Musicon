package com.Musicom.api.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Image;
import com.Musicom.data.model.Track;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.TrackSummaryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component("apiAlbumMapper")
@AllArgsConstructor
public class AlbumMapper implements IMap <AlbumDto, Album> {
    private final ImageMapper imageMapper;

    @Override
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

        List<TrackSummaryDto> trackSummaries = new ArrayList<>();
        for (Track track : album.getTracks()) {
            TrackSummaryDto trackSummary = new TrackSummaryDto();
            trackSummary.setId(track.getId());
            trackSummary.setName(track.getName());
            trackSummaries.add(trackSummary);
        }
        albumDto.setTracks(trackSummaries);

        album.getImages()
                .stream()
                .max(Comparator.comparing(Image::getHeight))
                .ifPresent(image -> albumDto.setImage(imageMapper.mapEntity(image)));

        return albumDto;
    }
}
