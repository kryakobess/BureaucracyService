package com.accounting.bureaucracyservice.service.validator.impl;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.DocumentValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentValidatorImplTest {

    DocumentValidator documentValidator = new DocumentValidatorImpl();

    @Test
    void validate() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType(DocumentType.IDENTITY_PASSPORT.name())
                .number("1234567890")
                .build();

        assertDoesNotThrow(() -> documentValidator.validate(createDto));
    }

    @Test
    void validate_nullNumber() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType(DocumentType.IDENTITY_PASSPORT.name())
                .number(null)
                .build();

        assertThrows(BadRequestException.class, () -> documentValidator.validate(createDto));
    }

    @Test
    void validate_nullType() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType(null)
                .number("1234567890")
                .build();

        assertThrows(BadRequestException.class, () -> documentValidator.validate(createDto));
    }

    @Test
    void validate_unknownType() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType("null")
                .number("1234567890")
                .build();

        assertThrows(BadRequestException.class, () -> documentValidator.validate(createDto));
    }

    @Test
    void validate_wrongNumber() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .number("1234567890")
                .build();

        assertThrows(BadRequestException.class, () -> documentValidator.validate(createDto));
    }

    @Test
    void validate_NumberAlphabet() {
        DocumentCreateDto createDto = DocumentCreateDto.builder()
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .number("1234a6789")
                .build();

        assertThrows(BadRequestException.class, () -> documentValidator.validate(createDto));
    }
}