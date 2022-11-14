package ru.practicum.explorewithme.exception;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final BadRequestException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final IllegalArgumentException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final PropertyValueException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(final ForbiddenException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequestException(final ConstraintViolationException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }
}
