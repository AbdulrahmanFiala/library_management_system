package com.fiala.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.Year;

@Entity
@Table(name ="book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 45, message = "Name cannot exceed 45 characters")
    @Column(name = "title")
    private String title;

    @Size(max = 45, message = "Author name cannot exceed 45 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "publication_year")
    private Year publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 13, message = "ISBN is a 13-digit number")
    @Column(name = "isbn")
    private  String isbn;
}
