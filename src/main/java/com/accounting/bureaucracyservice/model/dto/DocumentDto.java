package com.accounting.bureaucracyservice.model.dto;

import com.accounting.bureaucracyservice.model.enums.DocumentType;
import lombok.Builder;

@Builder
public record DocumentDto(
        Long id,
        DocumentType documentType,
        String number
) {
}
