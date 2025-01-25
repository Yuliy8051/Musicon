package com.Musicom.data.repository;

import com.Musicom.data.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findBySourceId(String sourceId);

    List<Album> findByName(String name);

    @Query("select count(a) from Album a")
    Long countAll();

    @Query(value = "select * from albums limit :limit offset :offset", nativeQuery = true)
    List<Album> findPage(@Param("offset") long offset, @Param("limit") int limit);
}
