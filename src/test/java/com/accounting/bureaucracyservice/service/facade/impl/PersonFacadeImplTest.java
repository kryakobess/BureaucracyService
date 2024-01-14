package com.accounting.bureaucracyservice.service.facade.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.service.CitizenService;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFacadeImplTest {

    @Mock
    private CitizenService citizenService;

    @Mock
    private CitizenMapper citizenMapper;

    @InjectMocks
    private PersonFacadeImpl personFacade;

    @Test
    void createPerson() {
        var createDto = CitizenCreateDto.builder().build();
        var citizen = new Citizen();
        when(citizenService.createCitizen(any())).thenReturn(citizen);
        when(citizenMapper.toDto(any())).thenReturn(CitizenDto.builder().build());

        personFacade.createPerson(createDto);

        verify(citizenService).createCitizen(createDto);
        verify(citizenMapper).toDto(citizen);
    }

    @Test
    void getPersonById() {
        long id = 1L;
        var citizen = new Citizen();
        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);
        when(citizenMapper.toDto(any())).thenReturn(CitizenDto.builder().build());

        personFacade.getPersonById(id);

        verify(citizenService).getCitizenById(id);
        verify(citizenMapper).toDto(citizen);
    }
}