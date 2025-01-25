package com.Musicom.api.mapper;

public interface IMap <IDto, IEntity> {
    IEntity mapDto(IDto dto);

    IDto mapEntity(IEntity entity);
}
