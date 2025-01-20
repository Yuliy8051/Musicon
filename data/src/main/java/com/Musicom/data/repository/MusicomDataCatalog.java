package com.Musicom.data.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class MusicomDataCatalog {
        private final AlbumTypeRepository albumType;
        private final AlbumRepository album;
        private final BandRepository band;
        private final GenreRepository genre;
        private final ImageRepository image;
        private final MarketRepository market;
        private final TrackRepository track;
}
