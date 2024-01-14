package com.accounting.bureaucracyservice.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Date;
import java.time.LocalDate;

@Builder
public record CitizenCreateDto(
        String firstName,
        String secondName,
        LocalDate dateOfBirth,
        @Size(max = 12, min = 12)
        String phoneNumber,
        Integer passportSeries,
        Integer passportNumber,
        AddressCreateDto registrationAddress
) {
}
