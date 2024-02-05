package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
public record ChangeCitizenDto(
        String firstName,
        String secondName,
        String phoneNumber
) {
}
