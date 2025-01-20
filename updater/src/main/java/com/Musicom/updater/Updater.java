package com.Musicom.updater;

import com.Musicom.data.model.*;
import com.Musicom.data.repository.MusicomDataCatalog;
import com.Musicom.spotify_client.client.SpotifyClient;
import com.Musicom.spotify_client.dto.*;
import com.Musicom.spotify_client.provider.TokenProvider;
import com.Musicom.updater.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Updater {
    private final MusicomDataCatalog db;

    private final SpotifyClient client;

    private final DtoMapper mapper;

    private TokenProvider tokenProvider;

    public void updateByDate(LocalDate date) {
        client.setToken(tokenProvider.fetchRefreshedToken());

        updateAlbumTypes();
        updateMarkets();

        List<TrackDto> tracksDto = fetchTracksDto(date);

        updateBands(tracksDto);
        updateAlbums(tracksDto);
        updateTracks(tracksDto);
    }

    private List<TrackDto> fetchTracksDto(LocalDate date) {
        PagedResultDto pagedResult = client
                .fetchPageByDate(date)
                .result();

        int offset = pagedResult.offset();
        int total = pagedResult.total();
        int limit = pagedResult.limit();

        List<TrackDto> tracksDto = new ArrayList<>();
        for (int i = offset; i < total; i += limit) {
            tracksDto.addAll(client
                    .fetchPageByDate(date, i)
                    .result()
                    .tracks());
        }
        return tracksDto;
    }

    private void updateMarkets() {
        if (db.market().findFirst() == null) {
            List<String> marketNames = client
                    .fetchMarkets()
                    .markets();
            for (String name : marketNames) {
                Market market = mapper
                        .market()
                        .map(name);
                db.market().save(market);
            }
        }
    }

    private void updateAlbumTypes() {
        if (db.albumType().findFirst() == null) {
            db.albumType().save(new AlbumType("single"));
            db.albumType().save(new AlbumType("album"));
            db.albumType().save(new AlbumType("compilation"));
        }
    }

    private void updateBands(List<TrackDto> tracksDto) {
        List<String> bandIds = tracksDto
                .stream()
                .flatMap(t -> t.bandIdsDto().stream())
                .map(TrackDto.BandIdDto::id)
                .toList();

        for (String id : bandIds) {
            Band existingBand = db
                    .band()
                    .findBySourceId(id);
            BandDto bandDto = client.fetchBand(id);
            Band band = mapper
                    .band()
                    .map(bandDto);

            if (existingBand != null)
                band.setId(existingBand.getId());

            db.band().save(band);

            updateImages(id, bandDto);
        }
    }

    private void updateAlbums(List<TrackDto> tracksDto) {
        List<AlbumDto> albumsDto = tracksDto
                .stream()
                .map(TrackDto::album)
                .toList();

        for (AlbumDto albumDto : albumsDto) {
            Album existingAlbum = db
                    .album()
                    .findBySourceId(albumDto.id());
            Album album = mapper
                    .album()
                    .map(albumDto);
            if (existingAlbum != null)
                album.setId(existingAlbum.getId());

            db.album().save(album);

            updateImages(albumDto.id(), albumDto);
        }
    }

    private void updateImages(String id, BandDto bandDto) {
        List<ImageDto> imagesDto = bandDto.images();
        for (ImageDto imageDto : imagesDto) {
            Image image = mapper.image().mapForBand(id, imageDto);
            db.image().save(image);
        }
    }

    private void updateImages(String id, AlbumDto albumDto) {
        List<ImageDto> imagesDto = albumDto.images();
        for (ImageDto imageDto : imagesDto) {
            Image image = mapper.image().mapForAlbum(id, imageDto);
            db.image().save(image);
        }
    }

    private void updateTracks(List<TrackDto> tracksDto) {
        for (TrackDto trackDto : tracksDto) {
            Track existingTrack = db
                    .track()
                    .findBySourceId(trackDto.id());
            if (existingTrack == null){
                Track track = mapper
                        .track()
                        .map(trackDto);
                db.track().save(track);
            }
        }
    }
}