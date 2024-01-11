package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CitizenValidatorImplTest {

    @Mock
    private AddressValidator addressValidator;

    @InjectMocks
    private CitizenValidatorImpl validator;

    @Test
    public void validateSuccessfully() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(Date.valueOf(LocalDate.now()))
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(567890)
                .registrationAddress(AddressDto.builder().build())
                .build();

        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    public void validationFailedRequiredFieldIsNull() {
        assertThrows(BadRequestException.class,
                () -> validator.validate(CitizenCreateDto.builder().build()));
    }

    @Test
    public void invalidPassportSeries() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(Date.valueOf(LocalDate.now()))
                .firstName("First")
                .secondName("Second")
                .passportSeries(12345)
                .passportNumber(567890)
                .registrationAddress(AddressDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }

    @Test
    public void invalidPassportNumber() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(Date.valueOf(LocalDate.now()))
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(5678490)
                .registrationAddress(AddressDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }
}