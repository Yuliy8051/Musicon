package com.Musicom.api.mapper;

import com.Musicom.data.model.Band;
import com.Musicom.data.model.Genre;
import com.Musicom.data.model.Image;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.GenreRepository;
import com.Musicom.web_api_contract.BandDto;
import com.Musicom.web_api_contract.ImageDto;
import com.Musicom.web_api_contract.SummaryDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class BandMapperTest {
    @Mock
    private ImageMapper imageMapper;

    @Mock
    private SummaryMapper<Track> trackSummaryMapper;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BandMapper bandMapper;

    public BandMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnSeveralDtos() {
        Band band1 = mock(Band.class);
        Band band2 = mock(Band.class);
        List<Band> bands = spy(List.of(band1, band2));
        Stream<Band> firstStream = (Stream<Band>) mock(Stream.class);
        Stream<BandDto> streamAfterMap = (Stream<BandDto>) mock(Stream.class);
        List<BandDto> expected = List.of(mock(BandDto.class), mock(BandDto.class));

        when(bands.stream()).thenReturn(firstStream);
        when(firstStream.map(any(Function.class))).thenReturn(streamAfterMap);
        when(streamAfterMap.toList()).thenReturn(expected);

        List<BandDto> result = bandMapper.mapAllEntities(bands);

        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnEntity() {
        Image image = new Image(
                null,
                "http://example.com/7",
                456,
                456,
                null,
                null
        );
        Genre genre = new Genre();
        genre.setName("genre name");
        Band band = new Band(
                null,
                null,
                "http://example.com/",
                "band test",
                37,
                Set.of(image),
                Set.of(genre),
                null
        );

        ImageDto imageDto = new ImageDto(null, "http://example.com/7", 456, 456);
        List<String> genres = List.of("genre 1", "genre 2");
        BandDto bandDto = new BandDto(
                null,
                "http://example.com/",
                "band test",
                37,
                imageDto,
                genres,
                null
        );

        when(imageMapper.mapDto(imageDto)).thenReturn(image);
        when(genreRepository.findByNameIn(genres)).thenReturn(Set.of(genre));

        Band result = bandMapper.mapDto(bandDto);

        assertEquals(band, result);
    }
    @Test
    public void shouldReturnDto() {
        Image image = new Image(
                7L,
                "http://example.com/7",
                456,
                456,
                null,
                null
        );
        Genre genre1 = new Genre();
        genre1.setName("genre 1");
        Genre genre2 = new Genre();
        genre2.setName("genre 2");
        Track track1 = new Track();
        track1.setId(69L);
        track1.setName("track1 name");
        Track track2 = new Track();
        track2.setId(70L);
        track2.setName("track2 name");
        Set<Track> tracks = Set.of(track1, track2);
        Band band = new Band(
                3L,
                null,
                "http://example.com/",
                "band test",
                37,
                Set.of(image),
                Set.of(genre1, genre2),
                tracks
        );

        ImageDto imageDto = new ImageDto(null, "http://example.com/7", 456, 456);
        List<String> genres = List.of("genre 1", "genre 2");
        SummaryDto summary1 = new SummaryDto(69L, "track1 name");
        SummaryDto summary2 = new SummaryDto(70L, "track2 name");
        List<SummaryDto> summaries = List.of(summary1, summary2);
        BandDto bandDto = new BandDto(
                3L,
                "http://example.com/",
                "band test",
                37,
                imageDto,
                genres,
                summaries
        );

        when(imageMapper.mapEntity(image)).thenReturn(imageDto);
        when(trackSummaryMapper.mapAllEntities(tracks)).thenReturn(summaries);

        BandDto result =  bandMapper.mapEntity(band);

        assertEquals(bandDto, result);
    }
}
