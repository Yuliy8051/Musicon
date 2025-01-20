package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Market;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.TrackDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class TrackMapper implements IMap<TrackDto, Track> {
    private final MusicomDataCatalog db;

    @Override
    public Track map(TrackDto trackDto) {
        Track track = new Track();
        track.setSourceId(trackDto.id());
        track.setDurationMs(trackDto.durationMs());
        track.setUrl(trackDto.urlDto().url());
        track.setName(trackDto.name());
        track.setPopularity(trackDto.popularity());

        Album album = db
                .album()
                .findBySourceId(trackDto.album().id());
        track.setAlbum(album);

        Set<Market> markets = db
                .market()
                .findByNameIn(trackDto.markets());
        track.setAvailableMarkets(markets);

        Set<Band> bands = db
                .band()
                .findBySourceIdIn(
                        trackDto
                        .bandIdsDto()
                        .stream()
                        .map(TrackDto.BandIdDto::id)
                        .toList()
                );
        track.setBands(bands);

        return track;
    }
}
