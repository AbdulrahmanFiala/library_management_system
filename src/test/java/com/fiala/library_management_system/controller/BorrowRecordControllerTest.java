package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.service.RecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowRecordControllerTest {

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RecordController recordController;

    private Long testBookId;
    private Long testPatronId;

    @BeforeEach
    void setUp() {
        testBookId = 1L;
        testPatronId = 2L;
    }

    @Test
    void testBorrowBook_Success() throws Exception {
        // Arrange
        when(recordService.borrowBook(testBookId, testPatronId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = recordController.borrowBook(testBookId, testPatronId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book borrowed successfully", response.getBody());
        verify(recordService, times(1)).borrowBook(testBookId, testPatronId);
    }

    @Test
    void testBorrowBook_Failure() throws Exception {
        // Arrange
        String errorMessage = "Book not available";
        when(recordService.borrowBook(testBookId, testPatronId))
                .thenThrow(new Exception(errorMessage));

        // Act
        ResponseEntity<String> response = recordController.borrowBook(testBookId, testPatronId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody());
        verify(recordService, times(1)).borrowBook(testBookId, testPatronId);
    }

    @Test
    void testBorrowBook_NullBookId() throws Exception {
        // Arrange
        when(recordService.borrowBook(null, testPatronId))
                .thenThrow(new IllegalArgumentException("Book ID cannot be null"));

        // Act
        ResponseEntity<String> response = recordController.borrowBook(null, testPatronId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(recordService, times(1)).borrowBook(null, testPatronId);
    }

    @Test
    void testBorrowBook_NullPatronId() throws Exception {
        // Arrange
        when(recordService.borrowBook(testBookId, null))
                .thenThrow(new IllegalArgumentException("Patron ID cannot be null"));

        // Act
        ResponseEntity<String> response = recordController.borrowBook(testBookId, null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(recordService, times(1)).borrowBook(testBookId, null);
    }

    @Test
    void testReturnBook_Success() throws Exception {
        // Arrange
        doNothing().when(recordService).returnBook(testBookId, testPatronId);

        // Act
        ResponseEntity<String> response = recordController.returnBook(testBookId, testPatronId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book returned successfully", response.getBody());
        verify(recordService, times(1)).returnBook(testBookId, testPatronId);
    }

    @Test
    void testReturnBook_Failure() throws Exception {
        // Arrange
        String errorMessage = "Book not returned";
        doThrow(new Exception(errorMessage))
                .when(recordService).returnBook(testBookId, testPatronId);

        // Act
        ResponseEntity<String> response = recordController.returnBook(testBookId, testPatronId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody());
        verify(recordService, times(1)).returnBook(testBookId, testPatronId);
    }

    @Test
    void testReturnBook_RecordNotFound() throws Exception {
        // Arrange
        String errorMessage = "No borrow record found";
        doThrow(new Exception(errorMessage))
                .when(recordService).returnBook(testBookId, testPatronId);

        // Act
        ResponseEntity<String> response = recordController.returnBook(testBookId, testPatronId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody());
        verify(recordService, times(1)).returnBook(testBookId, testPatronId);
    }

    @Test
    void testReturnBook_NullBookId() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Book ID cannot be null"))
                .when(recordService).returnBook(null, testPatronId);

        // Act
        ResponseEntity<String> response = recordController.returnBook(null, testPatronId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(recordService, times(1)).returnBook(null, testPatronId);
    }
}