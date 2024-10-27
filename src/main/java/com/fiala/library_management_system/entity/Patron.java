package com.fiala.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name ="patron")
@Data
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @NotBlank(message = "Patron name is mandatory")
    @Column(name = "name")
    private String name;

    @Size(max = 255, message = "Email cannot exceed 255 characters")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Patron email is mandatory")
    @Column(name = "email")
    private String email;

    @Size(max = 20, message = "Phone number can't exceed 20 characters")
    @Pattern(regexp = "\\+?[0-9]+", message = "Invalid phone number format")
    @Column(name = "phone_number")
    private String phoneNumber;
}
