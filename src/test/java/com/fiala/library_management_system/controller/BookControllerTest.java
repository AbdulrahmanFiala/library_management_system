package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.entity.Book;
import com.fiala.library_management_system.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book testBook1;
    private Book testBook2;

    @BeforeEach
    void setUp() {
        testBook1 = new Book(1L, "1984", "George Orwell", Year.of(2000), "978-0451524935");
        testBook2 = new Book(2L, "Brave New World", "Aldous Huxley", Year.of(1932), "978-0060850524");
    }

    @Test
    void testGetAllBooks_Success() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(testBook1, testBook2));

        // Act
        List<Book> books = bookController.getAllBooks();

        // Assert
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(testBook1, books.get(0));
        assertEquals(testBook2, books.get(1));
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetAllBooks_EmptyList() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(List.of());

        // Act
        List<Book> books = bookController.getAllBooks();

        // Assert
        assertNotNull(books);
        assertTrue(books.isEmpty());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_Found() {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook1));

        // Act
        ResponseEntity<Book> response = bookController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testBook1.getTitle(), response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        // Arrange
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> response = bookController.getBookById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(99L);
    }

    @Test
    void testCreateBook_Success() {
        // Arrange
        Book newBook = new Book(null, "New Book", "Author", Year.of(2024), "978-1234567890");
        Book savedBook = new Book(3L, "New Book", "Author", Year.of(2024), "978-1234567890");
        when(bookService.saveBook(newBook)).thenReturn(savedBook);

        // Act
        Book result = bookController.createBook(newBook);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("New Book", result.getTitle());
        assertEquals(Year.of(2024), result.getPublicationYear());
        verify(bookService, times(1)).saveBook(newBook);
    }

    @Test
    void testUpdateBook_Success() {
        // Arrange
        Book bookDetails = new Book(null, "Updated Title", "Updated Author", Year.of(2023), "978-1234567890");
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", Year.of(2023), "978-1234567890");
        when(bookService.updateBook(1L, bookDetails)).thenReturn(updatedBook);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(1L, bookDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals(Year.of(2023), response.getBody().getPublicationYear());
        verify(bookService, times(1)).updateBook(1L, bookDetails);
    }

    @Test
    void testUpdateBook_NotFound() {
        // Arrange
        Book bookDetails = new Book(null, "Updated Title", "Updated Author", Year.of(2023), "978-1234567890");
        when(bookService.updateBook(99L, bookDetails))
                .thenThrow(new RuntimeException("Book not found"));

        // Act
        ResponseEntity<Book> response = bookController.updateBook(99L, bookDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).updateBook(99L, bookDetails);
    }

    @Test
    void testDeleteBook_Success() {
        // Arrange
        doNothing().when(bookService).deleteBook(1L);

        // Act
        var response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }
}