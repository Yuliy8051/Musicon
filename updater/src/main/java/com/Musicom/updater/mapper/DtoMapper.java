package com.Musicom.updater.mapper;

import org.springframework.stereotype.Component;

@Component
public record DtoMapper(MarketMapper market,
                        BandMapper band,
                        ImageMapper image,
                        AlbumMapper album,
                        TrackMapper track) {
}
