package com.Musicon.updater.mapper;

public interface IMap <IDto, IEntity> {
    IEntity map(IDto dto);
}
