package com.accounting.bureaucracyservice.model.dto;

import java.util.List;

public record DocumentsGetDto(
        Long citizenId,
        List<DocumentDto> documents
) {
}
