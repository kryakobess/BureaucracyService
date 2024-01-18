package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
public record DocumentCreateDto(
        String documentType,
        String number
) {
}
