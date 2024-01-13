package com.accounting.bureaucracyservice.controller.advice;

import com.accounting.bureaucracyservice.model.dto.ApiError;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BureaucracyControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> processBadRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage()));
    }
}
