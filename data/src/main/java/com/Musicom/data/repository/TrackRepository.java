package com.Musicom.data.repository;

import com.Musicom.data.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Track findBySourceId(String sourceId);

    List<Track> findByName(String name);

    @Query(value = "select count(t) from Track t")
    Long countAll();

    @Query(value = "select * from tracks order by id desc limit :limit offset :offset", nativeQuery = true)
    List<Track> findPage(@Param("offset") long offset, @Param("limit") int limit);
}
