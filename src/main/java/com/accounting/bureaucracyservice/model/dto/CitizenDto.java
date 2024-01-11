package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
public record CitizenDto(
        Long id,
        String firstName,
        String secondName,
        LocalDate dateOfBirth,
        List<DocumentDto> documents,
        List<AddressDto> addresses,
        boolean approvedAccount
) {
}
