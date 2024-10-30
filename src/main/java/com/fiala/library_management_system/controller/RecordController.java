package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecordController {
    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId)
    {
        try {
            recordService.borrowBook(bookId, patronId);
            return ResponseEntity.ok("Book borrowed successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId)
    {
        try {
            recordService.returnBook(bookId, patronId);
            return ResponseEntity.ok("Book returned successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
