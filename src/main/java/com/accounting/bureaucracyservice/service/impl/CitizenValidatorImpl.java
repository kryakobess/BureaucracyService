package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.CitizenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CitizenValidatorImpl implements CitizenValidator {

    @Override
    public void validate(CitizenCreateDto citizenCreateDto) {
        checkNotNull(citizenCreateDto.firstName(), "firstName");
        checkNotNull(citizenCreateDto.secondName(), "secondName");
        checkNotNull(citizenCreateDto.registrationAddress(), "registrationAddress");
        checkNotNull(citizenCreateDto.passportSeries(), "passportSeries");
        checkNotNull(citizenCreateDto.passportNumber(), "passportNumber");
    }

    private void checkNotNull(Object object, String fieldName) {
        if (Objects.isNull(object)) {
            throw new BadRequestException(String.format("Empty field %s caused validation error", fieldName));
        }
    }
}
