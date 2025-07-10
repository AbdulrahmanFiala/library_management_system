package com.fiala.library_management_system.service;

import com.fiala.library_management_system.entity.Patron;
import com.fiala.library_management_system.dao.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patronDetails", key = "#id")
    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CachePut(value = "patronDetails", key = "#id")
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found with id: " + id));

        patron.setName(patronDetails.getName());
        patron.setEmail(patronDetails.getEmail());
        patron.setPhoneNumber(patronDetails.getPhoneNumber());

        return patronRepository.save(patron);
    }

    @CacheEvict(value = "patronDetails", key = "#id")
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
