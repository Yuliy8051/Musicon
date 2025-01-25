package com.Musicom.api.mapper;

import com.Musicom.web_api_contract.TrackDto;
import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Track;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("apiTrackMapper")
@AllArgsConstructor
public class TrackMapper implements IMap<TrackDto, Track> {
    private final SummaryMapper<Band> bandSummaryMapper;

    private final SummaryMapper<Album> albumSummaryMapper;

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
        trackDto.setAlbum(albumSummaryMapper.mapEntity(track.getAlbum()));
        trackDto.setBands(bandSummaryMapper.mapAllEntities(track.getBands()));
        return trackDto;
    }

    public List<TrackDto> mapAllEntities(List<Track> tracks) {
        return tracks
                .stream()
                .map(this::mapEntity)
                .toList();
    }
}
