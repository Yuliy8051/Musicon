package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Image;
import com.Musicom.data.repository.AlbumRepository;
import com.Musicom.data.repository.BandRepository;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.ImageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ImageMapperTest {
    @Mock
    private MusicomDataCatalog db;

    @InjectMocks
    private ImageMapper imageMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnMappedImageForBand() {
        String id = "UUID";
        String url = "http://example.com/image.jpg";
        int width = 600;
        int height = 400;
        ImageDto imageDto = new ImageDto(url, height, width);
        BandRepository bandRepository = mock(BandRepository.class);
        Band band = mock(Band.class);

        when(db.band()).thenReturn(bandRepository);
        when(bandRepository.findBySourceId(id)).thenReturn(band);


        Image image = imageMapper.mapForBand(id, imageDto);
        assertEquals(url, image.getUrl());
        assertEquals(width, image.getWidth());
        assertEquals(height, image.getHeight());
        assertEquals(band, image.getBand());

        verify(db).band();
        verify(bandRepository).findBySourceId(id);
    }
    @Test
    public void shouldReturnMappedImageForAlbum() {
        String id = "UUID";
        String url = "http://example.com/image.jpg";
        int width = 600;
        int height = 400;
        ImageDto imageDto = new ImageDto(url, height, width);
        AlbumRepository albumRepository = mock(AlbumRepository.class);
        Album album = mock(Album.class);

        when(db.album()).thenReturn(albumRepository);
        when(albumRepository.findBySourceId(id)).thenReturn(album);

        Image result = imageMapper.mapForAlbum(id, imageDto);
        assertEquals(url, result.getUrl());
        assertEquals(width, result.getWidth());
        assertEquals(height, result.getHeight());
        assertEquals(album, result.getAlbum());

        verify(db).album();
        verify(albumRepository).findBySourceId(id);
    }
}
