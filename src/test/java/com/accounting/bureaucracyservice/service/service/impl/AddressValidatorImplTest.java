package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.impl.AddressValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressValidatorImplTest {

    private final AddressValidatorImpl validator = new AddressValidatorImpl();

    @Test
    public void validateSuccessfully() {
        var dto = AddressCreateDto.builder()
                .region("Нижегородский")
                .city("Нижний Новгород")
                .street("Родионова")
                .houseNumber("136")
                .index(666222)
                .build();

        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    public void validationFailedRequiredFieldIsNull() {
        assertThrows(BadRequestException.class,
                () -> validator.validate(AddressCreateDto.builder().build()));
    }
}