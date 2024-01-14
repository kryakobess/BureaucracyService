package com.accounting.bureaucracyservice.service.facade;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;

public interface PersonFacade {
    CitizenDto createPerson(CitizenCreateDto createDto);

    CitizenDto getPersonById(long id);
}
