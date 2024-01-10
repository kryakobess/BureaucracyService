package com.accounting.bureaucracyservice.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public record AddressDto(
        Long id,
        Integer index,
        String region,
        String city,
        String street,
        String houseNumber,
        String apartment
) {
}
