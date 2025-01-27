package com.Musicom.api.service;

import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.AlbumMapper;
import com.Musicom.data.model.Album;
import com.Musicom.data.repository.AlbumRepository;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {
    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    public AlbumServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAlbumDtoList() {
        String name = "   test name ";
        List<Album> albums = spy(List.of(mock(Album.class)));
        List<AlbumDto> albumsDto = spy(List.of(mock(AlbumDto.class)));

        when(albumRepository.findByName("test name")).thenReturn(albums);
        when(albumMapper.mapAllEntities(albums)).thenReturn(albumsDto);

        var result = albumService.getByName(name);

        assertEquals(albumsDto, result);
        verify(albumRepository).findByName(name.trim());
        verify(albums).isEmpty();
        verify(albumMapper).mapAllEntities(albums);
    }
    @Test
    public void shouldThrowAlbumNotFoundException() {
        String name = "   test name ";
        when(albumRepository.findByName("test name")).thenReturn(List.of());
        assertThrows(NotFoundException.AlbumNotFoundException.class, () -> albumService.getByName(name));
    }
    @Test
    public void shouldReturnPagedAlbumsDto() {
        long total = 366L;
        int page = 5;
        int offset = 200;
        int totalPages = 8;
        List<Album> albums = List.of(mock(Album.class), mock(Album.class));
        List<AlbumDto> albumsDto = List.of(mock(AlbumDto.class), mock(AlbumDto.class));

        when(albumRepository.countAll()).thenReturn(total);
        when(albumRepository.findPage(offset, 50)).thenReturn(albums);
        when(albumMapper.mapAllEntities(albums)).thenReturn(albumsDto);

        PagedAlbumsDto result = albumService.getPage(page);

        PagedAlbumsDto expected = new PagedAlbumsDto(page, total, totalPages, albumsDto);

        assertEquals(expected, result);
    }
    @Test
    public void shouldThrowPageNotFoundException() {
        int page = 50;
        long total = 366L;
        when(albumRepository.countAll()).thenReturn(total);
        assertThrows(NotFoundException.PageNotFoundException.class, () -> albumService.getPage(page));
    }
}
