package com.accounting.bureaucracyservice.service.facade;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonFacade {
    CitizenDto createPerson(CitizenCreateDto createDto);

    CitizenDto getPersonById(long id);

    Page<CitizenDto> getCitizenPages(
            List<Long> ids,
            List<String> firstNames,
            List<String> secondNames,
            List<String> regions,
            List<String> cities,
            List<String> streets,
            List<String> houseNumbers,
            List<String> apartments,
            Pageable pageable
    );
}
