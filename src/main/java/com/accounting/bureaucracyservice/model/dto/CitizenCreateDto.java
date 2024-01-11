package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

import java.sql.Date;

@Builder
public record CitizenCreateDto(
        String firstName,
        String secondName,
        Date dateOfBirth,
        Integer passportSeries,
        Integer passportNumber,
        AddressDto registrationAddress
) {
}
