package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressValidatorImplTest {

    private final AddressValidatorImpl validator = new AddressValidatorImpl();

    @Test
    public void validateSuccessfully() {
        var dto = AddressDto.builder()
                .id(1L)
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
                () -> validator.validate(AddressDto.builder().build()));
    }
}