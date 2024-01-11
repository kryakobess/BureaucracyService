package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.dto.AddressDto;

public interface AddressValidator extends Validator{
    void validate(AddressDto addressDto);
}
