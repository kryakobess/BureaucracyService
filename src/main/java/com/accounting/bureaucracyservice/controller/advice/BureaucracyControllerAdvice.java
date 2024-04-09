package com.accounting.bureaucracyservice.controller.advice;

import com.accounting.bureaucracyservice.model.dto.ApiError;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BureaucracyControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> processBadRequest(Exception e) {
        return getResponseWithErrorStatus(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> processNotFound(Exception e) {
        return getResponseWithErrorStatus(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> processInternalError(Exception e) {
        return getResponseWithErrorStatus(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<ApiError> getResponseWithErrorStatus(HttpStatus httpStatus, Exception e) {
        log.error("Error in api", e);
        return ResponseEntity.status(httpStatus)
                .body(new ApiError(httpStatus.getReasonPhrase(), e.getMessage()));
    }
}
