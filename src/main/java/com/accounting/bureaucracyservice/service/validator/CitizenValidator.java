package com.accounting.bureaucracyservice.service.validator;

import com.accounting.bureaucracyservice.model.dto.ChangeCitizenDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;

public interface CitizenValidator extends Validator{
    void validate(CitizenCreateDto citizenCreateDto);
    void validate(ChangeCitizenDto changeCitizenDto);
}
