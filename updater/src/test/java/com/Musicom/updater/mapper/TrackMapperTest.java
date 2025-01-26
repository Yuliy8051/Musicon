package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Market;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.AlbumRepository;
import com.Musicom.data.repository.BandRepository;
import com.Musicom.data.repository.MarketRepository;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.AlbumDto;
import com.Musicom.spotify_client.dto.TrackDto;
import com.Musicom.spotify_client.dto.UrlDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackMapperTest {
    @Mock
    private MusicomDataCatalog db;

    @InjectMocks
    private TrackMapper trackMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnMappedTrack() {
        String trackId = "track123";
        int durationMs = 300000;
        String url = "http://example.com/track123";
        String trackName = "Track Name";
        int popularity = 80;
        String albumId = "album123";
        List<String> markets = List.of("US", "GB");
        List<TrackDto.BandIdDto> bandIdsDto = List.of(new TrackDto.BandIdDto("band123"));
        TrackDto trackDto = new TrackDto(
                trackId,
                durationMs,
                new UrlDto(url),
                trackName,
                popularity,
                markets,
                new AlbumDto(albumId, 5, null, null, null, null, null, null),
                bandIdsDto
        );
        Album album = mock(Album.class);
        Market market = mock(Market.class);
        Band band = mock(Band.class);
        AlbumRepository albumRepository = mock(AlbumRepository.class);
        BandRepository bandRepository = mock(BandRepository.class);
        MarketRepository marketRepository = mock(MarketRepository.class);

        when(db.album()).thenReturn(albumRepository);
        when(db.band()).thenReturn(bandRepository);
        when(db.market()).thenReturn(marketRepository);
        when(albumRepository.findBySourceId(albumId)).thenReturn(album);
        when(marketRepository.findByNameIn(markets)).thenReturn(Set.of(market));
        when(bandRepository.findBySourceIdIn(List.of("band123"))).thenReturn(Set.of(band));

        Track track = trackMapper.map(trackDto);

        assertEquals(trackId, track.getSourceId());
        assertEquals(durationMs, track.getDurationMs());
        assertEquals(url, track.getUrl());
        assertEquals(trackName, track.getName());
        assertEquals(popularity, track.getPopularity());
        assertEquals(album, track.getAlbum());
        assertEquals(Set.of(market), track.getAvailableMarkets());
        assertEquals(Set.of(band), track.getBands());

        verify(db).album();
        verify(db).market();
        verify(db).band();
        verify(albumRepository).findBySourceId(albumId);
        verify(marketRepository).findByNameIn(markets);
        verify(bandRepository).findBySourceIdIn(List.of("band123"));
    }
}
