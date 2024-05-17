package com.fiala.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Table(name ="record")
@Data
public class Record {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @NotNull(message = "Borrow date must not be null")
        @Past(message = "Borrow date must be in the past")
        @Column(name = "borrow_date")
        private LocalDate borrowDate;

        @Future(message = "Return date must be in the future")
        @Column(name = "return_date")
        private LocalDate returnDate;

        @ManyToOne
        @JoinColumn(name = "book_id", nullable = false)
        private Book book;

        @ManyToOne
        @JoinColumn(name = "patron_id", nullable = false)
        private Patron patron;
}
