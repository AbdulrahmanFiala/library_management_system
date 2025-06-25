package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.entity.Patron;
import com.fiala.library_management_system.service.PatronService;
import com.fiala.library_management_system.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patrons")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }


    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        return patronService.getPatronById(id)
                .map(patron -> ResponseEntity.ok().body(patron))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patron createPatron(@Valid @RequestBody Patron patron) {
        return patronService.savePatron(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patronDetails) {
        try {
            Patron updatedPatron = patronService.updatePatron(id, patronDetails);
            return ResponseEntity.ok(updatedPatron);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        try {
            patronService.deletePatron(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
