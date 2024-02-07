package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
public record CitizenInfoDto(
        String firstName,
        String secondName,
        String documentType,
        String documentNumber
) {
}
