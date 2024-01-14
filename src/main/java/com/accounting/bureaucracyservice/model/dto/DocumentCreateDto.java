package com.accounting.bureaucracyservice.model.dto;

import com.accounting.bureaucracyservice.model.enums.DocumentType;

public record DocumentCreateDto(
        DocumentType documentType,
        String number
) {
}
