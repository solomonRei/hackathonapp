package com.hackathon.diasporadialog.exceptions.exceptionHandler;

import com.hackathon.diasporadialog.exceptions.JWTInvalidException;
import com.hackathon.diasporadialog.exceptions.UserNotFoundException;
import com.hackathon.diasporadialog.exceptions.UserNotGrantedToDoActionException;
import com.hackathon.diasporadialog.exceptions.ValidationCustomException;
import com.hackathon.diasporadialog.exceptions.exceptionHandler.ErrorDetailsInfo.ErrorDetails;
import com.hackathon.diasporadialog.exceptions.exceptionHandler.ErrorDetailsInfo.ValidationErrorDetails;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatusCode status,
                                                               @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationCustomException.class})
    public ResponseEntity<ValidationErrorDetails> handleValidationErrors(ValidationCustomException ex, WebRequest request) {
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(LocalDate.now(), ex.getErrorObjectMap(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(401));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(400));
    }

    @ExceptionHandler({UserNotGrantedToDoActionException.class})
    public ResponseEntity<ErrorDetails> handleUserNotGrantedToDoActionException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(401));
    }

    @ExceptionHandler({JWTInvalidException.class})
    public ResponseEntity<ErrorDetails> handleJWTVerificationException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
