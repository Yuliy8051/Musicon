package com.Musicom.data.repository;

import com.Musicom.data.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BandRepository extends JpaRepository<Band, Long> {
    Band findBySourceId(String sourceId);
    Set<Band> findBySourceIdIn(List<String> sourceIds);
}
