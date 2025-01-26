package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.AlbumType;
import com.Musicom.data.model.Market;
import com.Musicom.data.repository.AlbumTypeRepository;
import com.Musicom.data.repository.MarketRepository;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.AlbumDto;
import com.Musicom.spotify_client.dto.UrlDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlbumMapperTest {
    @Mock
    private MusicomDataCatalog db;

    @InjectMocks
    private AlbumMapper albumMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnMappedAlbumWhenReleaseDateContainsOnlyYear() {
        UrlDto urlDto = spy(new UrlDto("testUrl"));
        AlbumDto albumDto = spy(new AlbumDto(
                "UUID",
                4,
                urlDto,
                "TestName",
                "2024",
                null,
                "expected",
                List.of("PL", "US")
        ));
        AlbumTypeRepository albumTypeRepository = mock(AlbumTypeRepository.class);
        AlbumType albumType = mock(AlbumType.class);
        MarketRepository marketRepository = mock(MarketRepository.class);
        Set<Market> markets = Set.of(mock(Market.class), mock(Market.class));

        when(db.albumType()).thenReturn(albumTypeRepository);
        when(albumTypeRepository.findByName("expected")).thenReturn(albumType);
        when(db.market()).thenReturn(marketRepository);
        when(marketRepository.findByNameIn(List.of("PL", "US"))).thenReturn(markets);

        Album result = albumMapper.map(albumDto);

        verify(albumDto).id();
        verify(albumDto).totalTracks();
        verify(albumDto).urlDto();
        verify(urlDto).url();
        verify(albumDto).name();
        verify(albumDto).releaseDate();
        verify(albumDto).albumType();

        Album expected = new Album();
        expected.setSourceId(albumDto.id());
        expected.setTotalTracks(albumDto.totalTracks());
        expected.setUrl(albumDto.urlDto().url());
        expected.setName(albumDto.name());
        expected.setReleaseDate(LocalDate.of(2024,1,1));
        expected.setAlbumType(albumType);
        expected.setAvailableMarkets(markets);
        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnMappedAlbumWhenReleaseDateContainsAllInfo() {
        UrlDto urlDto = spy(new UrlDto("testUrl"));
        AlbumDto albumDto = spy(new AlbumDto(
                "UUID",
                4,
                urlDto,
                "TestName",
                "2024-04-16",
                null,
                "expected",
                List.of("PL", "US")
        ));
        AlbumTypeRepository albumTypeRepository = mock(AlbumTypeRepository.class);
        AlbumType albumType = mock(AlbumType.class);
        MarketRepository marketRepository = mock(MarketRepository.class);
        Set<Market> markets = Set.of(mock(Market.class), mock(Market.class));

        when(db.albumType()).thenReturn(albumTypeRepository);
        when(albumTypeRepository.findByName("expected")).thenReturn(albumType);
        when(db.market()).thenReturn(marketRepository);
        when(marketRepository.findByNameIn(List.of("PL", "US"))).thenReturn(markets);

        Album result = albumMapper.map(albumDto);

        verify(albumDto).id();
        verify(albumDto).totalTracks();
        verify(albumDto).urlDto();
        verify(urlDto).url();
        verify(albumDto).name();
        verify(albumDto).releaseDate();
        verify(albumDto).albumType();

        Album expected = new Album();
        expected.setSourceId(albumDto.id());
        expected.setTotalTracks(albumDto.totalTracks());
        expected.setUrl(albumDto.urlDto().url());
        expected.setName(albumDto.name());
        expected.setReleaseDate(LocalDate.of(2024,4,16));
        expected.setAlbumType(albumType);
        expected.setAvailableMarkets(markets);
        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnMappedAlbumWhenReleaseDateContainsOnlyYearAndMonth() {
        UrlDto urlDto = spy(new UrlDto("testUrl"));
        AlbumDto albumDto = spy(new AlbumDto(
                "UUID",
                4,
                urlDto,
                "TestName",
                "2024-06",
                null,
                "expected",
                List.of("PL", "US")
        ));
        AlbumTypeRepository albumTypeRepository = mock(AlbumTypeRepository.class);
        AlbumType albumType = mock(AlbumType.class);
        MarketRepository marketRepository = mock(MarketRepository.class);
        Set<Market> markets = Set.of(mock(Market.class), mock(Market.class));

        when(db.albumType()).thenReturn(albumTypeRepository);
        when(albumTypeRepository.findByName("expected")).thenReturn(albumType);
        when(db.market()).thenReturn(marketRepository);
        when(marketRepository.findByNameIn(List.of("PL", "US"))).thenReturn(markets);

        Album result = albumMapper.map(albumDto);

        verify(albumDto).id();
        verify(albumDto).totalTracks();
        verify(albumDto).urlDto();
        verify(urlDto).url();
        verify(albumDto).name();
        verify(albumDto).releaseDate();
        verify(albumDto).albumType();

        Album expected = new Album();
        expected.setSourceId(albumDto.id());
        expected.setTotalTracks(albumDto.totalTracks());
        expected.setUrl(albumDto.urlDto().url());
        expected.setName(albumDto.name());
        expected.setReleaseDate(LocalDate.of(2024,6,1));
        expected.setAlbumType(albumType);
        expected.setAvailableMarkets(markets);
        assertEquals(expected, result);
    }
}
