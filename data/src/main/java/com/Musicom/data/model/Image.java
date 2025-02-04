package com.Musicom.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @ManyToOne
    private Band band;

    @ManyToOne
    private Album album;
}
