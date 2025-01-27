package com.Musicom.api.service;

import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.TrackMapper;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.TrackRepository;
import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.web_api_contract.TrackDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TrackServiceTest {
    @Mock
    private TrackRepository trackRepository;

    @Mock
    private TrackMapper trackMapper;

    @InjectMocks
    private TrackService trackService;

    public TrackServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTrackDtoList() {
        String name = "   test name ";
        List<Track> tracks = spy(List.of(mock(Track.class)));
        List<TrackDto> tracksDto = spy(List.of(mock(TrackDto.class)));

        when(trackRepository.findByName("test name")).thenReturn(tracks);
        when(trackMapper.mapAllEntities(tracks)).thenReturn(tracksDto);

        var result = trackService.getByName(name);

        assertEquals(tracksDto, result);
        verify(trackRepository).findByName(name.trim());
        verify(tracks).isEmpty();
        verify(trackMapper).mapAllEntities(tracks);
    }
    @Test
    public void shouldThrowTrackNotFoundException() {
        String name = "   test name ";
        when(trackRepository.findByName("test name")).thenReturn(List.of());
        assertThrows(NotFoundException.TrackNotFoundException.class, () -> trackService.getByName(name));
    }
    @Test
    public void shouldReturnPagedTracksDto() {
        long total = 366L;
        int page = 5;
        int offset = 200;
        int totalPages = 8;
        List<Track> tracks = List.of(mock(Track.class), mock(Track.class));
        List<TrackDto> tracksDto = List.of(mock(TrackDto.class), mock(TrackDto.class));

        when(trackRepository.countAll()).thenReturn(total);
        when(trackRepository.findPage(offset, 50)).thenReturn(tracks);
        when(trackMapper.mapAllEntities(tracks)).thenReturn(tracksDto);

        PagedTracksDto result = trackService.getPage(page);

        PagedTracksDto expected = new PagedTracksDto(page, total, totalPages, tracksDto);

        assertEquals(expected, result);
    }
    @Test
    public void shouldThrowPageNotFoundException() {
        int page = 50;
        long total = 366L;
        when(trackRepository.countAll()).thenReturn(total);
        assertThrows(NotFoundException.PageNotFoundException.class, () -> trackService.getPage(page));
    }
}
