package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
public record CheckCitizenExistsDto(
        String firstName,
        String secondName,
        String documentType,
        String documentNumber
) {
}
