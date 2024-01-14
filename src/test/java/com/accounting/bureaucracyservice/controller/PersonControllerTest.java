package com.accounting.bureaucracyservice.controller;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonFacade personFacade;

    @InjectMocks
    private PersonController personController;

    @Test
    void createPerson() {
        var createDto = CitizenCreateDto.builder().build();
        when(personFacade.createPerson(any())).thenReturn(CitizenDto.builder().build());

        personController.createPerson(createDto);

        verify(personFacade).createPerson(createDto);
    }

    @Test
    void getPersonById() {
        long id = 1L;
        when(personFacade.getPersonById(anyLong())).thenReturn(CitizenDto.builder().build());

        personController.getPersonById(id);

        verify(personFacade).getPersonById(id);
    }
}