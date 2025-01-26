package com.Musicom.updater.mapper;

import com.Musicom.data.model.Band;
import com.Musicom.data.model.Genre;
import com.Musicom.data.repository.GenreRepository;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.BandDto;
import com.Musicom.spotify_client.dto.UrlDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BandMapperTest {
    @Mock
    private MusicomDataCatalog db;

    @InjectMocks
    private BandMapper bandMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnMappedBand() {
        String id = "123";
        String name = "Test Band";
        String url = "http://test.com";
        int popularity = 80;
        List<String> genresNames = List.of("Rock", "Pop");
        BandDto bandDto = new BandDto(
                id,
                new UrlDto(url),
                name,
                popularity,
                null,
                genresNames
        );
        Genre rockGenre = new Genre();
        rockGenre.setName("Rock");
        Set<Genre> genres = Set.of(rockGenre);
        GenreRepository genreRepository = mock(GenreRepository.class);

        when(db.genre()).thenReturn(genreRepository);
        when(genreRepository.findByName("Rock")).thenReturn(rockGenre);
        when(genreRepository.findByName("Pop")).thenReturn(null);
        when(genreRepository.findByNameIn(genresNames)).thenReturn(genres);

        Band result = bandMapper.map(bandDto);

        assertNotNull(result);
        assertEquals(id, result.getSourceId());
        assertEquals(name, result.getName());
        assertEquals(url, result.getUrl());
        assertEquals(popularity, result.getPopularity());
        assertEquals(genres, result.getGenres());

        verify(db.genre()).findByName("Rock");
        verify(db.genre()).findByName("Pop");
        verify(db.genre()).findByNameIn(genresNames);
    }
}
