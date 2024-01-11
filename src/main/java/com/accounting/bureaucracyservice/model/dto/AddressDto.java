package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
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
