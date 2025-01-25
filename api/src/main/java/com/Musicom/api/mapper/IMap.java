package com.Musicom.api.mapper;

import java.util.List;

public interface IMap <IDto, IEntity> {
    IEntity mapDto(IDto dto);

    IDto mapEntity(IEntity entity);;

    List<IDto> mapAllEntities(List<IEntity> entities);
}
