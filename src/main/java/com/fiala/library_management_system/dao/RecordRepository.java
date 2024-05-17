package com.fiala.library_management_system.dao;

import com.fiala.library_management_system.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByPatronIdAndBookId(Long patronId, Long bookId);

}
