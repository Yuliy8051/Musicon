package com.Musicom.data.repository;

import com.Musicom.data.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
    Set<Genre> findByNameIn(List<String> names);
}
