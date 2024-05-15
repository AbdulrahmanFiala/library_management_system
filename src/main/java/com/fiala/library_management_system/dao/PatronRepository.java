package com.fiala.library_management_system.dao;

import com.fiala.library_management_system.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
