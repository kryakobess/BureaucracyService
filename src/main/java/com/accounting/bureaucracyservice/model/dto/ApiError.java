package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        String reason
) {
}
