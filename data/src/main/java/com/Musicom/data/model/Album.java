package com.Musicom.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@Setter
@Getter
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sourceId;

    @Column(nullable = false)
    private int totalTracks;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @OneToMany
    private Set<Image> images;

    @OneToMany
    private Set<Track> tracks;

    @ManyToOne
    private AlbumType albumType;

    @ManyToMany
    private Set<Market> availableMarkets;

    @ManyToMany
    private Set<Genre> genres;
}
