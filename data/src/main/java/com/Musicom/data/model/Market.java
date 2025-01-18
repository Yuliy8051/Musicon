package com.Musicom.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "markets")
@NoArgsConstructor
@Setter
@Getter
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "availableMarkets")
    private Set<Track> tracks;

    @ManyToMany(mappedBy = "availableMarkets")
    private Set<Album> albums;
}
