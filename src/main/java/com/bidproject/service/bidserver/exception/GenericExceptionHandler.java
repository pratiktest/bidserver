package com.bidproject.service.bidserver.exception;

import com.bidproject.service.bidserver.project.ProjectExpiredException;
import com.bidproject.service.bidserver.seller.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception e, WebRequest w){
        GenericException genericException = new GenericException(e.getMessage(), w.getDescription(false));
        return new ResponseEntity<Object>(genericException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleSellerNotFoundException(NotFoundException e, WebRequest w){
        GenericException genericException = new GenericException(e.getMessage(), w.getDescription(false));
        return new ResponseEntity<Object>(genericException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectExpiredException.class)
    public final ResponseEntity<Object> handleProjectExpired(ProjectExpiredException e, WebRequest w){
        GenericException genericException = new GenericException(e.getMessage(), w.getDescription(false));
        return new ResponseEntity<Object>(genericException, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        GenericException genericException = new GenericException("Invalid Request", ex.getBindingResult().toString());
        return new ResponseEntity<Object>(genericException, HttpStatus.BAD_REQUEST);
    }
}
