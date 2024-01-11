package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import org.springframework.stereotype.Service;

@Service
public class AddressValidatorImpl implements AddressValidator {

    @Override
    public void validate(AddressCreateDto dto) {
        checkNotNull(dto.city(), "city");
        checkNotNull(dto.houseNumber(), "houseNumber");
        checkNotNull(dto.region(), "region");
        checkNotNull(dto.street(), "street");
        checkNotNull(dto.index(), "index");
    }
}
