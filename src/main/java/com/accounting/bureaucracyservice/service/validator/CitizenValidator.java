package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;

public interface CitizenValidator extends Validator{
    void validate(CitizenCreateDto citizenCreateDto);
}
