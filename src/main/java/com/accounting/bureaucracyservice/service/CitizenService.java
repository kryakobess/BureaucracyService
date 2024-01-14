package com.accounting.bureaucracyservice.service;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;

public interface CitizenService {
    Citizen createCitizen(CitizenCreateDto citizenCreateDto);
    boolean isCitizenExistsByNameAndIdentityPassportNumber(String firstName, String secondName, String number);
    Citizen getCitizenById(long id);
}
