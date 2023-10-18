package com.authorsandbooksapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @OneToMany(mappedBy = "author")
    @JsonProperty
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    @Column(name = "first_name", nullable = false)
    @JsonProperty
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @JsonProperty
    private LocalDateTime birthDate;

    @Column(name = "comment")
    @JsonProperty
    private String comment;
}
