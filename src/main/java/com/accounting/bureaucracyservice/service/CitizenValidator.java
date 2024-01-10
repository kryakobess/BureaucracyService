package com.accounting.bureaucracyservice.service;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;

public interface CitizenValidator {
    void validate(CitizenCreateDto citizenCreateDto);
}
