package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;

import java.util.Objects;

public interface Validator {
    default void checkNotNull(Object object, String fieldName) {
        if (Objects.isNull(object)) {
            throw new BadRequestException(String.format("Empty field %s caused validation error", fieldName));
        }
    }
}
