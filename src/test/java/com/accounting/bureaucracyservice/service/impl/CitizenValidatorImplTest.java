package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import com.accounting.bureaucracyservice.service.validator.impl.CitizenValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                .dateOfBirth(LocalDate.now())
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(567890)
                .phoneNumber("+78005553535")
                .registrationAddress(AddressCreateDto.builder().build())
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
                .dateOfBirth(LocalDate.now())
                .firstName("First")
                .secondName("Second")
                .passportSeries(12345)
                .passportNumber(567890)
                .phoneNumber("+78005553535")
                .registrationAddress(AddressCreateDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }

    @Test
    public void invalidPassportNumber() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(LocalDate.now())
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(5678490)
                .phoneNumber("+78005553535")
                .registrationAddress(AddressCreateDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }

    @Test
    public void invalidPhoneNumber_invalidRegionOfNumber() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(LocalDate.now())
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(5678490)
                .phoneNumber("+32339998383")
                .registrationAddress(AddressCreateDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }

    @Test
    public void invalidPhoneNumber_invalidCount() {
        CitizenCreateDto dto = CitizenCreateDto.builder()
                .dateOfBirth(LocalDate.now())
                .firstName("First")
                .secondName("Second")
                .passportSeries(1234)
                .passportNumber(5678490)
                .phoneNumber("+3233998383")
                .registrationAddress(AddressCreateDto.builder().build())
                .build();

        assertThrows(BadRequestException.class,
                () -> validator.validate(dto));
    }
}