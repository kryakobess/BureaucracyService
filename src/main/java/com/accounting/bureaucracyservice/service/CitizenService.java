package com.accounting.bureaucracyservice.service;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;

public interface CitizenService {
    CitizenDto createCitizen(CitizenCreateDto citizenCreateDto);
    boolean isCitizenExistsByNameAndIdentityPassportNumber(String firstName, String secondName, String number);
}
