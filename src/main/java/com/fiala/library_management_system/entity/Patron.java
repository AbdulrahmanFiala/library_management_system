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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
