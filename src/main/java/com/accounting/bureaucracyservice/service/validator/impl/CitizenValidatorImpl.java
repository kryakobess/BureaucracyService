package com.accounting.bureaucracyservice.service.validator.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import com.accounting.bureaucracyservice.service.validator.CitizenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CitizenValidatorImpl implements CitizenValidator {

    private final AddressValidator addressValidator;
    private static final int PASSPORT_SERIES_LENGTH = 4;
    private static final int PASSPORT_NUMBER_LENGTH = 6;

    private static final String PHONE_NUMBER_REGEX = "\\+7\\d{10}";

    @Override
    public void validate(CitizenCreateDto citizenCreateDto) {
        checkNotNull(citizenCreateDto.firstName(), "firstName");
        checkNotNull(citizenCreateDto.secondName(), "secondName");
        checkNotNull(citizenCreateDto.registrationAddress(), "registrationAddress");
        checkNotNull(citizenCreateDto.passportSeries(), "passportSeries");
        checkNotNull(citizenCreateDto.passportNumber(), "passportNumber");

        checkPassportFormat(citizenCreateDto);
        checkPhoneNumber(citizenCreateDto.phoneNumber());
        addressValidator.validate(citizenCreateDto.registrationAddress());
    }

    private void checkPhoneNumber(String number) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            throw new BadRequestException("Inappropriate phone number format. Number format should be: +7xxxxxxxxxx");
        }
    }

    private void checkPassportFormat(CitizenCreateDto dto) {
        if (String.valueOf(dto.passportSeries()).length() != PASSPORT_SERIES_LENGTH
            || String.valueOf(dto.passportNumber()).length() != PASSPORT_NUMBER_LENGTH) {
            throw new BadRequestException("Inappropriate format of passport number");
        }
    }
}
