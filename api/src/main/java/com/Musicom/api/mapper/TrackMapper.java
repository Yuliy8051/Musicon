package com.Musicom.api.mapper;

import com.Musicom.web_api_contract.AlbumSummaryDto;
import com.Musicom.web_api_contract.BandSummaryDto;
import com.Musicom.web_api_contract.TrackDto;
import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Track;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("api")
public class TrackMapper implements IMap<TrackDto, Track> {
    @Override
    public Track mapDto(TrackDto trackDto) { // TODO
        return null;
    }

    @Override
    public TrackDto mapEntity(Track track) {
        TrackDto trackDto = new TrackDto();
        trackDto.setId(track.getId());
        trackDto.setName(track.getName());
        trackDto.setUrl(track.getUrl());
        trackDto.setPopularity(track.getPopularity());
        trackDto.setDurationMs(track.getDurationMs());

        Album album = track.getAlbum();
        AlbumSummaryDto albumSummaryDto = new AlbumSummaryDto();
        albumSummaryDto.setId(album.getId());
        albumSummaryDto.setName(album.getName());

        trackDto.setAlbum(albumSummaryDto);

        Set<Band> bands = track.getBands();
        Set<BandSummaryDto> bandSummariesDto = new HashSet<>();
        for (Band band : bands) {
            BandSummaryDto bandSummaryDto = new BandSummaryDto();
            bandSummaryDto.setId(band.getId());
            bandSummaryDto.setName(band.getName());
            bandSummariesDto.add(bandSummaryDto);
        }
        trackDto.setBands(bandSummariesDto);

        return trackDto;
    }

    @Override
    public List<Track> mapAllDtos(List<TrackDto> trackDtos) {  // TODO
        return List.of();
    }

    @Override
    public List<TrackDto> mapAllEntities(List<Track> tracks) {
        return tracks
                .stream()
                .map(this::mapEntity)
                .toList();
    }
}
