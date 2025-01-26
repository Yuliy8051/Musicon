package com.Musicom.api.service;

import com.Musicom.api.exception.NotFoundException;
import com.Musicom.web_api_contract.PagedTracksDto;
import com.Musicom.api.mapper.TrackMapper;
import com.Musicom.data.model.Track;
import com.Musicom.data.repository.TrackRepository;
import com.Musicom.web_api_contract.TrackDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrackService {
    private final TrackRepository repository;

    private final TrackMapper trackMapper;

    public PagedTracksDto getPage(int page) {
        int limit = 50;
        long offset = (long) (page - 1)  * limit;
        long total = repository.countAll();
        if (total <= offset)
            throw new NotFoundException.PageNotFoundException(page);
        int totalPages = (int) (total - 1) / limit + 1;
        List<Track> tracks = repository.findPage(offset, limit);
        PagedTracksDto pagedTracks = new PagedTracksDto();
        pagedTracks.setPage(page);
        pagedTracks.setTotal(total);
        pagedTracks.setTotalPages(totalPages);
        pagedTracks.setTracks(trackMapper.mapAllEntities(tracks));
        return pagedTracks;
    }

    public List<TrackDto> getByName(String name) {
        List<Track> tracks = repository.findByName(name.trim());
        if (tracks.isEmpty())
            throw new NotFoundException.TrackNotFoundException(name);
        return trackMapper.mapAllEntities(tracks);
    }
}
