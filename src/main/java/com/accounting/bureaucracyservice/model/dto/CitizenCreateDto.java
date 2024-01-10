package com.accounting.bureaucracyservice.model.dto;

import java.util.Date;

public record CitizenCreateDto(
        String firstName,
        String secondName,
        Date dateOfBirth,
        String passportSeries,
        String passportNumber,
        AddressDto registrationAddress
) {
}
