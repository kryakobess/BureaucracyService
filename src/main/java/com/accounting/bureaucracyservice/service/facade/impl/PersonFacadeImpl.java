package com.accounting.bureaucracyservice.service.facade.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.service.CitizenService;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonFacadeImpl implements PersonFacade {

    private final CitizenService citizenService;

    private final CitizenMapper citizenMapper;

    @Override
    public CitizenDto createPerson(CitizenCreateDto createDto) {
        log.info("Post request createPerson for citizen - {} {}", createDto.firstName(), createDto.secondName());
        return citizenMapper.toDto(citizenService.createCitizen(createDto));
    }

    @Override
    public CitizenDto getPersonById(long id) {
        log.info("Get request getPersonById for citizen with id - {}", id);
        return citizenMapper.toDto(citizenService.getCitizenById(id));
    }
}
