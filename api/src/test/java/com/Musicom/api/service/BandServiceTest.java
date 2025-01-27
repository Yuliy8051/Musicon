package com.Musicom.api.service;

import com.Musicom.api.exception.BadRequestException;
import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.BandMapper;
import com.Musicom.data.model.Band;
import com.Musicom.data.model.Image;
import com.Musicom.data.repository.BandRepository;
import com.Musicom.data.repository.ImageRepository;
import com.Musicom.web_api_contract.BandDto;
import com.Musicom.web_api_contract.PagedBandsDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BandServiceTest {
    @Mock
    private BandRepository bandRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private BandMapper bandMapper;

    @InjectMocks
    private BandService bandService;

    public BandServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnBandDtoList() {
        String name = "   test name ";
        List<Band> bands = spy(List.of(mock(Band.class)));
        List<BandDto> bandsDto = spy(List.of(mock(BandDto.class)));

        when(bandRepository.findByName("test name")).thenReturn(bands);
        when(bandMapper.mapAllEntities(bands)).thenReturn(bandsDto);

        var result = bandService.getByName(name);

        assertEquals(bandsDto, result);
        verify(bandRepository).findByName(name.trim());
        verify(bands).isEmpty();
        verify(bandMapper).mapAllEntities(bands);
    }
    @Test
    public void shouldThrowBandNotFoundExceptionInGetByName() {
        String name = "   test name ";
        when(bandRepository.findByName("test name")).thenReturn(List.of());
        assertThrows(NotFoundException.BandNotFoundException.class, () -> bandService.getByName(name));
    }
    @Test
    public void shouldReturnPagedBandsDto() {
        long total = 366L;
        int page = 5;
        int offset = 200;
        int totalPages = 8;
        List<Band> bands = List.of(mock(Band.class), mock(Band.class));
        List<BandDto> bandsDto = List.of(mock(BandDto.class), mock(BandDto.class));

        when(bandRepository.countAll()).thenReturn(total);
        when(bandRepository.findPage(offset, 50)).thenReturn(bands);
        when(bandMapper.mapAllEntities(bands)).thenReturn(bandsDto);

        PagedBandsDto result = bandService.getPage(page);

        PagedBandsDto expected = new PagedBandsDto(page, total, totalPages, bandsDto);

        assertEquals(expected, result);
    }
    @Test
    public void shouldThrowPageNotFoundException() {
        int page = 50;
        long total = 366L;
        when(bandRepository.countAll()).thenReturn(total);
        assertThrows(NotFoundException.PageNotFoundException.class, () -> bandService.getPage(page));
    }
    @Test
    public void shouldAddBand() {
        BandDto bandDto = mock(BandDto.class);
        String url = "test url";
        Band band = mock(Band.class);
        Set<Image> images = Set.of(mock(Image.class), mock(Image.class));

        when(bandDto.getUrl()).thenReturn(url);
        when(bandRepository.findByUrl(url)).thenReturn(null);
        when(bandMapper.mapDto(bandDto)).thenReturn(band);
        when(band.getImages()).thenReturn(images);

        bandService.add(bandDto);

        verify(bandDto).getUrl();
        verify(bandRepository).findByUrl(url);
        verify(bandRepository).save(band);
        verify(imageRepository).saveAll(images);
    }
    @Test
    public void shouldThrowUrlNotUniqueExceptionInsteadOfAdding() {
        BandDto bandDto = mock(BandDto.class);
        String url = "test url";
        Band bandForCheck = mock(Band.class);

        when(bandDto.getUrl()).thenReturn(url);
        when(bandRepository.findByUrl(url)).thenReturn(bandForCheck);

        assertThrows(BadRequestException.UrlNotUniqueException.class, () -> bandService.add(bandDto));
    }
    @Test
    public void shouldUpdateBandWhenUrlChangesAndUrlIsUnique() {
        BandDto bandDto = mock(BandDto.class);
        long id = 4L;
        Band existingBand = mock(Band.class);
        Optional<Band> optionalBand = spy(Optional.of(existingBand));
        String newUrl = "new url";
        String existingUrl = "existing url";
        Set<Image> imagesToDelete = Set.of(mock(Image.class), mock(Image.class));
        Band band = mock(Band.class);
        Set<Image> images = Set.of(mock(Image.class), mock(Image.class));

        when(bandDto.getId()).thenReturn(id);
        when(bandRepository.findById(id)).thenReturn(optionalBand);
        when(bandDto.getUrl()).thenReturn(newUrl);
        when(existingBand.getUrl()).thenReturn(existingUrl);
        when(bandRepository.findByUrl(newUrl)).thenReturn(null);
        when(existingBand.getImages()).thenReturn(imagesToDelete);
        when(bandMapper.mapDto(bandDto)).thenReturn(band);
        when(band.getImages()).thenReturn(images);

        bandService.update(bandDto);

        verify(optionalBand).isEmpty();
        verify(existingBand).getUrl();
        verify(bandRepository).findByUrl(newUrl);
        verify(imageRepository).deleteAll(imagesToDelete);
        verify(band).setId(id);
        verify(bandRepository).save(band);
        verify(imageRepository).saveAll(images);
    }
    @Test
    public void shouldUpdateBandWhenUrlDoesNotChange() {
        BandDto bandDto = mock(BandDto.class);
        long id = 4L;
        Band existingBand = mock(Band.class);
        Optional<Band> optionalBand = spy(Optional.of(existingBand));
        String newUrl = "url";
        String existingUrl = "url";
        Set<Image> imagesToDelete = Set.of(mock(Image.class), mock(Image.class));
        Band band = mock(Band.class);
        Set<Image> images = Set.of(mock(Image.class), mock(Image.class));

        when(bandDto.getId()).thenReturn(id);
        when(bandRepository.findById(id)).thenReturn(optionalBand);
        when(bandDto.getUrl()).thenReturn(newUrl);
        when(existingBand.getUrl()).thenReturn(existingUrl);
        when(existingBand.getImages()).thenReturn(imagesToDelete);
        when(bandMapper.mapDto(bandDto)).thenReturn(band);
        when(band.getImages()).thenReturn(images);

        bandService.update(bandDto);

        verify(optionalBand).isEmpty();
        verify(existingBand).getUrl();
        verify(bandRepository, times(0)).findByUrl(newUrl);
        verify(imageRepository).deleteAll(imagesToDelete);
        verify(band).setId(id);
        verify(bandRepository).save(band);
        verify(imageRepository).saveAll(images);
    }
    @Test
    public void shouldThrowBandNotFoundExceptionInUpdate() {
        BandDto bandDto = mock(BandDto.class);
        long id = 4L;
        Optional<Band> optionalBand = Optional.empty();

        when(bandDto.getId()).thenReturn(id);
        when(bandRepository.findById(id)).thenReturn(optionalBand);

        assertThrows(NotFoundException.BandNotFoundException.class, () -> bandService.update(bandDto));
    }
    @Test
    public void shouldThrowUrlNotUniqueExceptionInUpdate() {
        BandDto bandDto = mock(BandDto.class);
        long id = 4L;
        Band existingBand = mock(Band.class);
        Optional<Band> optionalBand = Optional.of(existingBand);
        String newUrl = "new url";
        String existingUrl = "existing url";

        when(bandDto.getId()).thenReturn(id);
        when(bandRepository.findById(id)).thenReturn(optionalBand);
        when(bandDto.getUrl()).thenReturn(newUrl);
        when(existingBand.getUrl()).thenReturn(existingUrl);
        when(bandRepository.findByUrl(newUrl)).thenReturn(mock(Band.class));

        assertThrows(BadRequestException.UrlNotUniqueException.class, () -> bandService.update(bandDto));
    }
    @Test
    public void shouldDeleteBand() {
        long id = 4L;
        Optional<Band> optionalBand = spy(Optional.of(mock(Band.class)));

        when(bandRepository.findById(id)).thenReturn(optionalBand);

        bandService.delete(id);

        verify(optionalBand).isEmpty();
        verify(bandRepository).deleteById(id);
    }
    @Test
    public void shouldThrowBandNotFoundExceptionInDelete() {
        long id = 4L;
        Optional<Band> optionalBand = Optional.empty();

        when(bandRepository.findById(id)).thenReturn(optionalBand);

        assertThrows(NotFoundException.BandNotFoundException.class, () -> bandService.delete(id));
    }
}
