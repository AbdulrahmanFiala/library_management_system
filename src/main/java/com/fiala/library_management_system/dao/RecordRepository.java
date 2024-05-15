package com.fiala.library_management_system.dao;

import com.fiala.library_management_system.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
