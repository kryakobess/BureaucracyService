package com.accounting.bureaucracyservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CitizenPageableDto(
        List<Long> ids,
        List<String> firstNames,
        List<String> secondNames,
        List<String> regions,
        List<String> cities,
        List<String> streets,
        List<String> houseNumbers,
        List<String> apartments
) {
}
