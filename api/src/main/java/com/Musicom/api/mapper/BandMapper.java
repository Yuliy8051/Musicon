package com.Musicom.api.mapper;

import com.Musicom.data.model.Band;
import com.Musicom.data.model.Genre;
import com.Musicom.data.model.Image;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.GenreRepository;
import com.Musicom.web_api_contract.BandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component("apiBandMapper")
@AllArgsConstructor
public class BandMapper implements IMap<BandDto, Band> {
    private final ImageMapper imageMapper;

    private final SummaryMapper<Track> trackSummaryMapper;

    private final GenreRepository genreRepository;

    @Override
    public Band mapDto(BandDto bandDto) {
        Band band = new Band();
        band.setUrl(bandDto.getUrl().trim());
        band.setName(bandDto.getName().trim());
        band.setPopularity(bandDto.getPopularity());
        Image image = imageMapper.mapDto(bandDto.getImage());
        image.setBand(band);
        band.setImages(Set.of(image));
        Set<Genre> genres = genreRepository.findByNameIn(bandDto.getGenres());
        band.setGenres(genres);
        return band;
    }

    @Override
    public BandDto mapEntity(Band band) {
        BandDto bandDto = new BandDto();
        bandDto.setId(band.getId());
        bandDto.setName(band.getName());
        bandDto.setUrl(band.getUrl());
        bandDto.setPopularity(band.getPopularity());
        band.getImages()
                .stream()
                .max(Comparator.comparing(Image::getHeight))
                .ifPresent(b -> bandDto.setImage(imageMapper.mapEntity(b)));
        bandDto.setGenres(
                band.getGenres()
                        .stream()
                        .map(Genre::getName)
                        .toList()
        );
        bandDto.setTracks(trackSummaryMapper.mapAllEntities(band.getTracks()));
        return bandDto;
    }

    public List<BandDto> mapAllEntities(List<Band> bands) {
        return bands
                .stream()
                .map(this::mapEntity)
                .toList();
    }
}
