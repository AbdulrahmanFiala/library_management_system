package com.fiala.library_management_system.service;

import com.fiala.library_management_system.dao.BookRepository;
import com.fiala.library_management_system.dao.PatronRepository;
import com.fiala.library_management_system.dao.RecordRepository;
import com.fiala.library_management_system.entity.Book;
import com.fiala.library_management_system.entity.Patron;
import com.fiala.library_management_system.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class RecordService {
    private BookRepository bookRepository;
    private PatronRepository patronRepository;
    private RecordRepository recordRepository;

    @Autowired
    public RecordService(BookRepository bookRepository, PatronRepository patronRepository, RecordRepository recordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.recordRepository = recordRepository;
    }

    public Book borrowBook(Long bookId, Long patronId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Patron> patron = patronRepository.findById(patronId);

        Optional<Record> validateBorrow = recordRepository.findByPatronIdAndBookId(patronId, bookId);

        if (book.isEmpty() || patron.isEmpty() || validateBorrow.isPresent()) {
            throw new Exception("Either Book or patron don't exist or the book already checked out by patron");
        }

        Record record = new Record(LocalDate.now(), book.get(), patron.get());

        recordRepository.save(record);

        return book.get();
    }

    public void returnBook(Long bookId, Long patronId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Patron> patron = patronRepository.findById(patronId);

        Optional<Record> validateBorrow = recordRepository.findByPatronIdAndBookId(patronId, bookId);

        if (book.isEmpty() || patron.isEmpty() || validateBorrow.isEmpty()) {
            throw new Exception("Either Book or patron don't exist or the book not checked out by patron");
        }

        Record record = validateBorrow.get();
        record.setReturnDate(LocalDate.now());
    }


}
