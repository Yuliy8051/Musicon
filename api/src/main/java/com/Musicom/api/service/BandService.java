package com.Musicom.api.service;

import com.Musicom.api.exception.NotFoundException;
import com.Musicom.api.mapper.BandMapper;
import com.Musicom.data.model.Band;
import com.Musicom.data.repository.BandRepository;
import com.Musicom.web_api_contract.PagedBandsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BandService {
    private BandRepository repository;

    private BandMapper bandMapper;

    public PagedBandsDto getPage(int page) {
        int limit = 50;
        long offset = (long) (page - 1)  * limit;
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
}
