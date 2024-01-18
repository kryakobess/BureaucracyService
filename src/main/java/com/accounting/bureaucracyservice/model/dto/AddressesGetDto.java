package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AddressesGetDto(
        Long citizenId,
        AddressDto registrationAddress,
        List<AddressDto> addresses
) {
}
