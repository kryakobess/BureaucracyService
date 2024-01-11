package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

import java.sql.Date;
import java.time.LocalDate;

@Builder
public record CitizenCreateDto(
        String firstName,
        String secondName,
        LocalDate dateOfBirth,
        Integer passportSeries,
        Integer passportNumber,
        AddressCreateDto registrationAddress
) {
}
