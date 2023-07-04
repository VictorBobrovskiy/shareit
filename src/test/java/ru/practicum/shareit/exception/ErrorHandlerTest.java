package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.UserNotFoundException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorHandlerTest {

    @Test
    public void handleValidation_ReturnsBadRequest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Exception exception = new ConstraintViolationException("Validation error", null);

        ErrorResponse errorResponse = errorHandler.handleValidation(exception);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getError());
    }

    @Test
    public void handleExists_ReturnsConflict() {
        ErrorHandler errorHandler = new ErrorHandler();
        ExistsException exception = new ExistsException("Conflict error");

        ErrorResponse errorResponse = errorHandler.handleExists(exception);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Conflict error", response.getBody().getError());
    }

    @Test
    public void handleUserNotFound_ReturnsNotFound() {
        ErrorHandler errorHandler = new ErrorHandler();
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ErrorResponse errorResponse = errorHandler.handleUserNotFound(exception);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getError());
    }

    @Test
    public void handleError_ReturnsInternalServerError() {
        ErrorHandler errorHandler = new ErrorHandler();
        Throwable exception = new Throwable("Internal error");

        ErrorResponse errorResponse = errorHandler.handleError(exception);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().getError());
    }
}
