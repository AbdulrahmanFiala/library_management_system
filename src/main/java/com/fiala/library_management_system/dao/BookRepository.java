package com.fiala.library_management_system.dao;

import com.fiala.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
}
