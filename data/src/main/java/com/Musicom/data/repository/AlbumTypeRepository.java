package com.Musicom.data.repository;

import com.Musicom.data.model.AlbumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumTypeRepository extends JpaRepository<AlbumType, Long> {
    @Query(value = "SELECT * FROM album_types LIMIT 1", nativeQuery = true)
    AlbumType findFirst();
    AlbumType findByName(String name);
}
