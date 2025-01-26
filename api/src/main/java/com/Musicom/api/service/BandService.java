package com.Musicom.api.service;

import com.Musicom.api.exception.BadRequestException;
import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.BandMapper;
import com.Musicom.data.model.Band;
import com.Musicom.data.repository.BandRepository;
import com.Musicom.data.repository.ImageRepository;
import com.Musicom.web_api_contract.BandDto;
import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BandService {
    private BandRepository repository;

    private ImageRepository imageRepository;

    private BandMapper bandMapper;

    public PagedBandsDto getPage(int page) {
        int limit = 50;
        long offset = (long) (page - 1) * limit;
        long total = repository.countAll();
        if (total <= offset)
            throw new NotFoundException.PageNotFoundException(page);
        int totalPages = (int) (total - 1) / limit + 1;
        List<Band> bands = repository.findPage(offset, limit);
        PagedBandsDto pagedBands = new PagedBandsDto();
        pagedBands.setPage(page);
        pagedBands.setTotal(total);
        pagedBands.setTotalPages(totalPages);
        pagedBands.setBands(bandMapper.mapAllEntities(bands));
        return pagedBands;
    }

    public List<BandDto> getByName(String name) {
        List<Band> bands = repository.findByName(name.trim());
        if (bands.isEmpty())
            throw new NotFoundException.BandNotFoundException(name);
        return bandMapper.mapAllEntities(bands);
    }

    public void add(BandDto bandDto) {
        if (repository.findByUrl(bandDto.getUrl()) != null)
            throw new BadRequestException.UrlNotUniqueException("Band");
        Band band = bandMapper.mapDto(bandDto);
        repository.save(band);
        imageRepository.saveAll(band.getImages());
    }
}
