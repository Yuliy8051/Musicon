package com.Musicom.updater.mapper;

import com.Musicom.data.model.Band;
import com.Musicom.data.model.Genre;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.BandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class BandMapper implements IMap<BandDto, Band> {
    private final MusicomDataCatalog db;

    @Override
    public Band map(BandDto bandDto) {
        Band band = new Band();
        band.setSourceId(bandDto.id());
        band.setName(bandDto.name());
        band.setUrl(bandDto.urlDto().url());
        band.setPopularity(bandDto.popularity());

        List<String> genresNames = bandDto.genres();

        for (String name : genresNames) {
            Genre genre = db.genre().findByName(name);
            if (genre == null) {
                genre = new Genre();
                genre.setName(name);
                db.genre().save(genre);
            }
        }

        Set<Genre> genres = db.genre().findByNameIn(genresNames);
        band.setGenres(genres);
        return band;
    }
}
