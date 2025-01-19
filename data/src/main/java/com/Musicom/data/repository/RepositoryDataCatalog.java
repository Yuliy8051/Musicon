package com.Musicom.data.repository;

import org.springframework.stereotype.Repository;

@Repository
public record RepositoryDataCatalog(
        AlbumTypeRepository albumType,
        AlbumRepository album,
        BandRepository band,
        GenreRepository genre,
        ImageRepository image,
        MarketRepository market,
        TrackRepository track) {
}
