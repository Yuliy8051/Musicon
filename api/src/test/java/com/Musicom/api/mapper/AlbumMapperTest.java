package com.Musicom.api.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.Image;
import com.Musicom.data.model.Track;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.ImageDto;
import com.Musicom.web_api_contract.SummaryDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AlbumMapperTest {
    @Mock
    private ImageMapper imageMapper;

    @Mock
    private SummaryMapper<Track> trackSummaryMapper;

    @InjectMocks
    private AlbumMapper albumMapper;

    public AlbumMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnNull() {
        assertNull(albumMapper.mapDto(new AlbumDto()));
    }
    @Test
    public void shouldReturnMappedDto() {
        Album album = new Album();
        album.setId(123L);
        album.setName("name123");
        album.setUrl("http://example.com");
        album.setReleaseDate(LocalDate.of(2008,6, 18));
        album.setTotalTracks(5);
        Track track1 = new Track();
        Track track2 = new Track();
        track1.setId(231L);
        track1.setName("Track1");
        track2.setId(221L);
        track2.setName("Track2");
        Set<Track> tracks = Set.of(track1, track2);
        album.setTracks(tracks);
        Image image1 = new Image();
        image1.setId(5L);
        image1.setUrl("http://example0.com");
        image1.setWidth(789);
        image1.setHeight(789);
        Image image2 = new Image();
        image2.setId(6L);
        image2.setUrl("http://example8.com");
        image2.setWidth(285);
        image2.setHeight(289);
        Set<Image> images = Set.of(image1, image2);
        album.setImages(images);

        List<SummaryDto> summariesDto = List.of(
                new SummaryDto(231L, "Track1"),
                new SummaryDto(232L, "Track2")
        );
        ImageDto imageDto1 = new ImageDto(5L, "http://example0.com", 789, 789);
        ImageDto imageDto2 = new ImageDto(6L, "http://example8.com", 285, 289);

        when(trackSummaryMapper.mapAllEntities(tracks)).thenReturn(summariesDto);
        when(imageMapper.mapEntity(image1)).thenReturn(imageDto1);
        when(imageMapper.mapEntity(image2)).thenReturn(imageDto2);

        AlbumDto result = albumMapper.mapEntity(album);

        AlbumDto expected = new AlbumDto();
        expected.setId(123L);
        expected.setUrl("http://example.com");
        expected.setTotalTracks(5);
        expected.setReleaseDate(LocalDate.of(2008,6, 18));
        expected.setName("name123");
        expected.setImage(imageDto1);

        expected.setTracks(summariesDto);

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnMappedDtos() {
        Album album1 = new Album();
        album1.setId(123L);
        album1.setName("name123");
        album1.setUrl("http://example.com");
        album1.setReleaseDate(LocalDate.of(2008,6, 18));
        album1.setTotalTracks(5);
        Track track1 = new Track();
        Track track2 = new Track();
        track1.setId(231L);
        track1.setName("Track1");
        track2.setId(221L);
        Set<Track> tracks1 = Set.of(track1, track2);
        album1.setTracks(tracks1);
        Image image1 = new Image();
        image1.setId(5L);
        image1.setUrl("http://example0.com");
        image1.setWidth(789);
        image1.setHeight(789);
        Image image2 = new Image();
        image2.setId(6L);
        image2.setUrl("http://example8.com");
        image2.setWidth(285);
        image2.setHeight(289);
        Set<Image> images1 = Set.of(image1, image2);
        album1.setImages(images1);

        Album album2 = new Album();
        album2.setId(124L);
        album2.setName("name124");
        album2.setUrl("http://example3.com");
        album2.setReleaseDate(LocalDate.of(2008,6, 18));
        album2.setTotalTracks(5);
        Track track3 = new Track();
        Track track4 = new Track();
        track3.setId(147L);
        track3.setName("Track3");
        track4.setId(741L);
        track4.setName("Track4");
        Set<Track> tracks2 = Set.of(track3, track4);
        album2.setTracks(tracks2);
        Image image3 = new Image();
        image3.setId(45L);
        image3.setUrl("http://example100.com");
        image3.setWidth(852);
        image3.setHeight(852);
        Image image4 = new Image();
        image4.setId(96L);
        image4.setUrl("http://example444.com");
        image4.setWidth(888);
        image4.setHeight(777);
        Set<Image> images2 = Set.of(image3, image4);
        album2.setImages(images2);
        List<Album> albums = List.of(album1, album2);

        List<SummaryDto> summariesDto1 = List.of(
                new SummaryDto(231L, "Track1"),
                new SummaryDto(232L, "Track2")
        );
        List<SummaryDto> summariesDto2 = List.of(
                new SummaryDto(147L, "Track3"),
                new SummaryDto(741L, "Track4")
        );
        ImageDto imageDto1 = new ImageDto(5L, "http://example0.com", 789, 789);
        ImageDto imageDto2 = new ImageDto(6L, "http://example8.com", 285, 289);
        ImageDto imageDto3 = new ImageDto(45L, "http://example100.com", 852, 852);
        ImageDto imageDto4 = new ImageDto(96L, "http://example444.com", 888, 777);


        when(trackSummaryMapper.mapAllEntities(tracks1)).thenReturn(summariesDto1);
        when(trackSummaryMapper.mapAllEntities(tracks2)).thenReturn(summariesDto2);
        when(imageMapper.mapEntity(image1)).thenReturn(imageDto1);
        when(imageMapper.mapEntity(image2)).thenReturn(imageDto2);
        when(imageMapper.mapEntity(image4)).thenReturn(imageDto4);
        when(imageMapper.mapEntity(image3)).thenReturn(imageDto3);


        AlbumDto albumDto1 = new AlbumDto();
        albumDto1.setId(123L);
        albumDto1.setUrl("http://example.com");
        albumDto1.setTotalTracks(5);
        albumDto1.setReleaseDate(LocalDate.of(2008,6, 18));
        albumDto1.setName("name123");
        albumDto1.setImage(imageDto1);
        albumDto1.setTracks(summariesDto1);

        AlbumDto albumDto2 = new AlbumDto();
        albumDto2.setId(124L);
        albumDto2.setUrl("http://example3.com");
        albumDto2.setTotalTracks(5);
        albumDto2.setReleaseDate(LocalDate.of(2008,6, 18));
        albumDto2.setName("name124");
        albumDto2.setImage(imageDto3);
        albumDto2.setTracks(summariesDto2);

        List<AlbumDto> expected = List.of(albumDto1, albumDto2);
        List<AlbumDto> result = albumMapper.mapAllEntities(albums);

        assertEquals(expected, result);

    }
}
