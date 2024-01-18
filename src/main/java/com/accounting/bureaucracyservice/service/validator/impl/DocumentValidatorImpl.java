package com.accounting.bureaucracyservice.service.validator.impl;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.DocumentValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DocumentValidatorImpl implements DocumentValidator {

    @Override
    public void validate(DocumentCreateDto createDto) {
        checkNotNull(createDto.documentType(), "documentType");
        checkNotNull(createDto.number(), "number");
        checkHasCorrectDocumentType(createDto.documentType());
        checkNumber(createDto.number(), createDto.documentType());
    }

    private void checkNumber(String number, String documentType) {
        DocumentType type = DocumentType.valueOf(documentType);
        if (number.length() != type.getNumbersCount() || !StringUtils.isNumeric(number)) {
            throw new BadRequestException("Incorrect number format!");
        }
    }

    private void checkHasCorrectDocumentType(String documentType) {
        boolean isCorrectType = Arrays.stream(DocumentType.values())
                .anyMatch(type -> type.name().equals(documentType));
        if (!isCorrectType) {
            throw new BadRequestException(String.format("Unknown documentType=%s", documentType));
        }
    }
}
