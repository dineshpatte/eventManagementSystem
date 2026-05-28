package com.dinesh.tickets.controllers;

import com.dinesh.tickets.Domain.DTO.ErrorDto;
import com.dinesh.tickets.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { QrCodeGenerationException.class })
    public ResponseEntity<ErrorDto> handleQrCodeNotFoundException(QrCodeGenerationException ex) {
        log.error("caught qr code not found exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to find QR code");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { TicketSoldOutException.class })
    public ResponseEntity<ErrorDto> handleTicketsSoldOutException(QrCodeGenerationException ex) {
        log.error("caught ticket sold out exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Tickets are  sold out for this Ticket type");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }



        @ExceptionHandler(value = { QrCodeGenerationException.class })
    public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException ex) {
        log.error("caught QrCode generation exception",ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to generate QR code");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @ExceptionHandler(value = { EventUpdateException.class })
    public ResponseEntity<ErrorDto> EventUpdateException(EventUpdateException ex) {
        log.error("caught event update exception",ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to update event");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { TicketTypeNotFoundException.class })
    public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
        log.error("ticket type not found",ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("ticket type not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { EventNotFoundException.class })
    public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex) {
        log.error("event not found",ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("event not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("user not found",ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("user not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        log.error("method argument not valid", ex);
        ErrorDto errorDto = new ErrorDto();
       BindingResult bindingResult =  ex.getBindingResult();
       List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      String errorMessage =  fieldErrors.stream().findFirst().map(fieldError -> fieldError.getField()+":"+fieldError.getDefaultMessage()).orElse("validation error occured");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex) {

        log.error("caught constraintViolationException", ex);
        ErrorDto errorDto = new ErrorDto();

        String errorMessage = ex.getConstraintViolations().stream().findFirst().map(violation -> violation.getPropertyPath().toString()).orElse("constraint violation occured");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("caught exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unknown error occured ");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
