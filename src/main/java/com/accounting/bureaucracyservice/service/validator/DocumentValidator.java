package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;

public interface DocumentValidator extends Validator {
    void validate(DocumentCreateDto createDto);
}
