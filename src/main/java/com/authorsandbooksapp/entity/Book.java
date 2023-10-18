package com.authorsandbooksapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonProperty
    private Author author;

    @Column(name = "title", nullable = false)
    @JsonProperty
    private String title;

    @Column(name = "published_at", nullable = false)
    @JsonProperty
    private LocalDateTime publishedAt;

    @Column(name = "genre", nullable = false)
    @JsonProperty
    private String genre;

}
