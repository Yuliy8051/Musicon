package com.Musicom.api.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Track;
import com.Musicom.web_api_contract.SummaryDto;
import com.Musicom.web_api_contract.TrackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TrackMapperTest {
    private SummaryMapper<Album> albumSummaryMapper;

    private SummaryMapper<Band> bandSummaryMapper;

    private TrackMapper trackMapper;

    @BeforeEach
    public void setUp() {
        albumSummaryMapper = (SummaryMapper<Album>) mock(SummaryMapper.class);
        bandSummaryMapper = (SummaryMapper<Band>) mock(SummaryMapper.class);
        trackMapper = new TrackMapper(bandSummaryMapper, albumSummaryMapper);
    }

    public TrackMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnNull() {
        assertNull(trackMapper.mapDto(mock(TrackDto.class)));
    }

    @Test
    public void shouldReturnDto() {
        Album album = new Album();
        album.setId(123L);
        album.setName("albumName");
        Band band = new Band();
        band.setId(147L);
        band.setName("bandName");
        Set<Band> bands = Set.of(band);
        Track track = new Track(
                1L,
                null,
                120123,
                "http://example.com/",
                "testTrack",
                45,
                album,
                null,
                bands
        );
        SummaryDto albumSummary = new SummaryDto(123L, "albumName");
        SummaryDto bandSummary1 = new SummaryDto(456L, "band1test");
        SummaryDto bandSummary2 = new SummaryDto(963L, "band2test");
        List<SummaryDto> bandSummaries = List.of(bandSummary1, bandSummary2);
        TrackDto expected = new TrackDto(
                1L,
                120123,
                "http://example.com/",
                "testTrack",
                45,
                albumSummary,
                bandSummaries
        );

        when(albumSummaryMapper.mapEntity(album)).thenReturn(albumSummary);
        when(bandSummaryMapper.mapAllEntities(bands)).thenReturn(bandSummaries);

        TrackDto result = trackMapper.mapEntity(track);

        assertEquals(expected, result);

        verify(albumSummaryMapper).mapEntity(album);
        verify(bandSummaryMapper).mapAllEntities(bands);
    }

    @Test
    public void shouldReturnSeveralDtos() {
        Track track1 = mock(Track.class);
        Track track2 = mock(Track.class);
        List<Track> tracks = spy(List.of(track1, track2));
        Stream<Track> firstStream = (Stream<Track>) mock(Stream.class);
        Stream<TrackDto> streamAfterMap = (Stream<TrackDto>) mock(Stream.class);
        List<TrackDto> expected = List.of(mock(TrackDto.class), mock(TrackDto.class));

        when(tracks.stream()).thenReturn(firstStream);
        when(firstStream.map(any(Function.class))).thenReturn(streamAfterMap);
        when(streamAfterMap.toList()).thenReturn(expected);

        List<TrackDto> result = trackMapper.mapAllEntities(tracks);

        assertEquals(expected, result);
    }
}
