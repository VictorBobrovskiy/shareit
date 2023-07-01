package ru.practicum.shareit.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.ItemRequest.ItemRequestNotFoundException;
import ru.practicum.shareit.booking.BookingNotFoundException;
import ru.practicum.shareit.item.ItemNotFoundException;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class,
            MethodArgumentNotValidException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleExists(final ExistsException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class, ItemNotFoundException.class, BookingNotFoundException.class,
            UserAccessException.class, ItemRequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(Exception e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(final Throwable e) {
        return new ErrorResponse("Internal Server Error");
    }

}
