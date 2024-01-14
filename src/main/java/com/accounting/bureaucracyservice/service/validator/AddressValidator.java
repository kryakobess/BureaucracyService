package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;

public interface AddressValidator extends Validator{
    void validate(AddressCreateDto addressDto);
}
