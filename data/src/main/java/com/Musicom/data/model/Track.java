package com.Musicom.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tracks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Track implements ICanBeSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sourceId;

    @Column(nullable = false)
    private int durationMs;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int popularity;

    @ManyToOne
    private Album album;

    @ManyToMany
    private Set<Market> availableMarkets;

    @ManyToMany
    private Set<Band> bands;
}
