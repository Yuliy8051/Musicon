package com.Musicom.api.service;

import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.AlbumMapper;
import com.Musicom.data.model.Album;
import com.Musicom.data.repository.AlbumRepository;
import com.Musicom.web_api_contract.AlbumDto;
import com.Musicom.web_api_contract.PagedAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {
    private AlbumMapper albumMapper;

    private AlbumRepository repository;

    public PagedAlbumsDto getPage(int page) {
        int limit = 50;
        long offset = (long) (page - 1)  * limit;
        long total = repository.countAll();
        if (total <= offset)
            throw new NotFoundException.PageNotFoundException(page);
        int totalPages = (int) (total - 1) / limit + 1;
        List<Album> albums = repository.findPage(offset, limit);
        PagedAlbumsDto pagedAlbums = new PagedAlbumsDto();
        pagedAlbums.setPage(page);
        pagedAlbums.setTotal(total);
        pagedAlbums.setTotalPages(totalPages);
        pagedAlbums.setAlbums(albumMapper.mapAllEntities(albums));
        return pagedAlbums;
    }

    public List<AlbumDto> getByName(String name) {
        List<Album> albums = repository.findByName(name.trim());
        if (albums.isEmpty())
            throw new NotFoundException.AlbumNotFoundException(name);
        return albumMapper.mapAllEntities(albums);
    }
}
