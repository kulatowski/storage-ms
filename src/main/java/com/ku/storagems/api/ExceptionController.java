package com.ku.storagems.api;

import com.ku.storagems.exception.UserFileNotFoundException;
import com.ku.storagems.exception.StorageNotFoundException;
import com.ku.storagems.exception.UserNotFoundException;
import com.microsoft.graph.http.GraphServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { StorageNotFoundException.class, UserNotFoundException.class, UserFileNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request) {
        log.error("Not found exception due to: {}", exception.getMessage());
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = GraphServiceException.class)
    protected ResponseEntity<Object> handleGeneralException(RuntimeException exception, WebRequest request) {
        log.error("Internal server error due to: {}", exception.getMessage());
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
