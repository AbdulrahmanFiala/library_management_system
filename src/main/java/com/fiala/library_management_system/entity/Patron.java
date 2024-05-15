package com.fiala.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;


@Entity
@Table(name ="patron")
@Data
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_year")
    private Year publicationYear;

    @Column(name = "isbn")
    private String isbn;

}
