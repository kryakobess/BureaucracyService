package com.accounting.bureaucracyservice.model.dto;

import com.accounting.bureaucracyservice.model.enums.DocumentType;

public record DocumentDto(
        Long id,
        DocumentType documentType,
        String number
) {
}
