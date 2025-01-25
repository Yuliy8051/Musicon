package com.Musicom.api.mapper;

import com.Musicom.data.model.ICanBeSummary;
import com.Musicom.web_api_contract.SummaryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SummaryMapper<IEntity extends ICanBeSummary> implements IMap<SummaryDto, IEntity> {
    @Override
    public IEntity mapDto(SummaryDto summaryDto) { // TODO
        return null;
    }

    @Override
    public SummaryDto mapEntity(IEntity iCanBeSummary) {
        return new SummaryDto(iCanBeSummary.getId(), iCanBeSummary.getName());
    }

    public List<SummaryDto> mapAllEntities(Set<IEntity> weCanBeSummary) {
        return weCanBeSummary
                .stream()
                .map(this::mapEntity)
                .toList();
    }
}
