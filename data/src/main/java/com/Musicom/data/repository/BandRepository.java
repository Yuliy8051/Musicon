package com.Musicom.data.repository;

import com.Musicom.data.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BandRepository extends JpaRepository<Band, Long> {
    Band findBySourceId(String sourceId);
    Set<Band> findBySourceIdIn(List<String> sourceIds);

    @Query(value = "select count(b) from Band b")
    Long countAll();

    @Query(value = "select * from bands limit :limit offset :offset", nativeQuery = true)
    List<Band> findPage(@Param("offset") long offset, @Param("limit") int limit);
}
