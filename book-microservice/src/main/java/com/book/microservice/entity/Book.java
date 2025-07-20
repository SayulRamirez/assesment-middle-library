package com.book.microservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    private String summary;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author authorId;

    @ManyToOne
    @JoinColumn(name = "literary_genre_id", nullable = false)
    private LiteraryGenre literaryGenre;
}
