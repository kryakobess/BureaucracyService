package com.accounting.bureaucracyservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum DocumentType {
    IDENTITY_PASSPORT(10),
    INTERNATIONAL_PASSPORT(9),
    DRIVER_LICENCE(10);

    private int numbersCount;
}
