package com.fiala.library_management_system.dao;

import com.fiala.library_management_system.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByPatronIdAndBookId(Long patronId, Long bookId);

}
