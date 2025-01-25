package com.Musicom.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "bands")
@Setter
@Getter
public class Band implements ICanBeSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sourceId;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int popularity;

    @OneToMany(mappedBy = "band")
    private Set<Image> images;

    @ManyToMany
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "bands")
    private Set<Track> tracks;
}
