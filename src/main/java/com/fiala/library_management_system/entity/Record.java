package com.fiala.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name ="record")
@Data
public class Record {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "borrow_date")
        private LocalDate borrowDate;

        @Column(name = "return_date")
        private LocalDate returnDate;

        @ManyToOne
        @JoinColumn(name = "book_id", nullable = false)
        private Book book;

        @ManyToOne
        @JoinColumn(name = "patron_id", nullable = false)
        private Patron patron;

}
