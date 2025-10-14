package com.fiala.library_management_system.controller;

import com.fiala.library_management_system.entity.Patron;
import com.fiala.library_management_system.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private Patron testPatron1;
    private Patron testPatron2;

    @BeforeEach
    void setUp() {
        testPatron1 = new Patron(1L, "John Doe", "john.doe@example.com", "123-456-7890");
        testPatron2 = new Patron(2L, "Jane Smith", "jane.smith@example.com", "098-765-4321");
    }

    @Test
    void testGetAllPatrons_Success() {
        // Arrange
        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(testPatron1, testPatron2));

        // Act
        List<Patron> patrons = patronController.getAllPatrons();

        // Assert
        assertNotNull(patrons);
        assertEquals(2, patrons.size());
        assertEquals(testPatron1, patrons.get(0));
        assertEquals(testPatron2, patrons.get(1));
        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void testGetAllPatrons_EmptyList() {
        // Arrange
        when(patronService.getAllPatrons()).thenReturn(List.of());

        // Act
        List<Patron> patrons = patronController.getAllPatrons();

        // Assert
        assertNotNull(patrons);
        assertTrue(patrons.isEmpty());
        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void testGetPatronById_Found() {
        // Arrange
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(testPatron1));

        // Act
        ResponseEntity<Patron> response = patronController.getPatronById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testPatron1.getName(), response.getBody().getName());
        assertEquals(testPatron1.getEmail(), response.getBody().getEmail());
        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    void testGetPatronById_NotFound() {
        // Arrange
        when(patronService.getPatronById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Patron> response = patronController.getPatronById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(patronService, times(1)).getPatronById(99L);
    }

    @Test
    void testCreatePatron_Success() {
        // Arrange
        Patron newPatron = new Patron(null, "New Patron", "new.patron@example.com", "111-222-3333");
        Patron savedPatron = new Patron(3L, "New Patron", "new.patron@example.com", "111-222-3333");
        when(patronService.savePatron(newPatron)).thenReturn(savedPatron);

        // Act
        Patron result = patronController.createPatron(newPatron);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("New Patron", result.getName());
        assertEquals("new.patron@example.com", result.getEmail());
        verify(patronService, times(1)).savePatron(newPatron);
    }

    @Test
    void testUpdatePatron_Success() {
        // Arrange
        Patron patronDetails = new Patron(null, "Updated Name", "updated.email@example.com", "999-888-7777");
        Patron updatedPatron = new Patron(1L, "Updated Name", "updated.email@example.com", "999-888-7777");
        when(patronService.updatePatron(1L, patronDetails)).thenReturn(updatedPatron);

        // Act
        ResponseEntity<Patron> response = patronController.updatePatron(1L, patronDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Name", response.getBody().getName());
        assertEquals("updated.email@example.com", response.getBody().getEmail());
        verify(patronService, times(1)).updatePatron(1L, patronDetails);
    }

    @Test
    void testUpdatePatron_NotFound() {
        // Arrange
        Patron patronDetails = new Patron(null, "Updated Name", "updated.email@example.com", "999-888-7777");
        when(patronService.updatePatron(99L, patronDetails))
                .thenThrow(new RuntimeException("Patron not found"));

        // Act
        ResponseEntity<Patron> response = patronController.updatePatron(99L, patronDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(patronService, times(1)).updatePatron(99L, patronDetails);
    }

    @Test
    void testDeletePatron_Success() {
        // Arrange
        doNothing().when(patronService).deletePatron(1L);

        // Act
        var response = patronController.deletePatron(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patronService, times(1)).deletePatron(1L);
    }
}