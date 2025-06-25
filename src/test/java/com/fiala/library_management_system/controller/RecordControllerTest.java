package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.service.RecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordControllerTest {

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RecordController recordController;

    public RecordControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBorrowBook_Success() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;
        when(recordService.borrowBook(bookId, patronId)).thenReturn(null);

        ResponseEntity<String> response = recordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book borrowed successfully", response.getBody());
    }

    @Test
    void testBorrowBook_Failure() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;
        String errorMessage = "Book not available";
        when(recordService.borrowBook(bookId, patronId)).thenThrow(new Exception(errorMessage));

        ResponseEntity<String> response = recordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testReturnBook_Success() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;

        doNothing().when(recordService).returnBook(bookId, patronId);

        ResponseEntity<String> response = recordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully", response.getBody());
    }

    @Test
    void testReturnBook_Failure() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;
        String errorMessage = "Book not returned";

        doThrow(new Exception(errorMessage)).when(recordService).returnBook(bookId, patronId);

        ResponseEntity<String> response = recordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
