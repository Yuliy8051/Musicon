package com.Musicom.api.mapper;

import com.Musicom.api.exception.NotImageLinkException;
import com.Musicom.api.image_link_checker.ImageLinkChecker;
import com.Musicom.data.model.Image;
import com.Musicom.web_api_contract.ImageDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ImageMapperTest {
    @Mock
    private ImageLinkChecker imageLinkChecker;

    @InjectMocks
    private ImageMapper imageMapper;

    public ImageMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnImage() {
        ImageDto imageDto = new ImageDto();
        imageDto.setUrl("   http://example.com  ");
        Image expected = new Image();
        expected.setUrl("http://example.com");
        expected.setHeight(640);
        expected.setWidth(640);

        when(imageLinkChecker.isImageLink("http://example.com")).thenReturn(true);

        Image result = imageMapper.mapDto(imageDto);

        assertEquals(expected.getUrl(), result.getUrl());
        assertEquals(expected.getWidth(), result.getWidth());
        assertEquals(expected.getHeight(), result.getHeight());
    }
    @Test
    public void shouldThrowNotImageLinkException() {
        ImageDto imageDto = new ImageDto();
        imageDto.setUrl("   http://example.com  ");

        when(imageLinkChecker.isImageLink("http://example.com")).thenReturn(false);

        assertThrows(NotImageLinkException.class, () -> imageMapper.mapDto(imageDto));
    }
    @Test
    public void shouldReturnImageDto() {
        Image image = new Image(1L, "http://example.com", 456, 852, null, null);
        ImageDto expected = new ImageDto(1L, "http://example.com", 456, 852);
        assertEquals(expected, imageMapper.mapEntity(image));
    }
}
