package com.Musicom.data.repository;

import com.Musicom.data.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MarketRepository extends JpaRepository<Market, Long> {
    @Query(value = "SELECT * FROM markets LIMIT 1", nativeQuery = true)
    Market findFirst();
    Set<Market> findByNameIn(List<String> names);
}
