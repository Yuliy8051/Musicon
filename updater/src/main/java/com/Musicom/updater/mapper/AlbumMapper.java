package com.Musicom.updater.mapper;

import com.Musicom.data.model.Album;
import com.Musicom.data.model.AlbumType;
import com.Musicom.data.model.Market;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@AllArgsConstructor
public class AlbumMapper implements IMap<AlbumDto, Album> {
    private final MusicomDataCatalog db;

    @Override
    public Album map(AlbumDto albumDto) {
        Album album = new Album();
        album.setSourceId(albumDto.id());
        album.setTotalTracks(albumDto.totalTracks());
        album.setUrl(albumDto.urlDto().url());
        album.setName(albumDto.name());

        String[] releaseDateParts = albumDto
                .releaseDate()
                .split("-");

        LocalDate releaseDate;
        if (releaseDateParts.length == 1) {
            releaseDate = LocalDate.of(
                    Integer.parseInt(releaseDateParts[0]),
                    1,
                    1
            );
        } else if (releaseDateParts.length == 2) {
            releaseDate = LocalDate.of(
                    Integer.parseInt(releaseDateParts[0]),
                    Integer.parseInt(releaseDateParts[1]),
                    1
            );
        } else {
            releaseDate = LocalDate.of(
                    Integer.parseInt(releaseDateParts[0]),
                    Integer.parseInt(releaseDateParts[1]),
                    Integer.parseInt(releaseDateParts[2])
            );
        }
        album.setReleaseDate(releaseDate);

        AlbumType albumType = db
                .albumType()
                .findByName(albumDto.albumType());
        album.setAlbumType(albumType);

        Set<Market> markets = db
                .market()
                .findByNameIn(albumDto.markets());
        album.setAvailableMarkets(markets);

        return album;
    }
}
