package com.Musicom.updater.mapper;

public interface IMap <IDto, IEntity> {
    IEntity map(IDto dto);
}
