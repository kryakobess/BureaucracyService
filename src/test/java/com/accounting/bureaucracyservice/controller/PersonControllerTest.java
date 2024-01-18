package com.accounting.bureaucracyservice.controller;

import com.accounting.bureaucracyservice.model.dto.*;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
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

    @Test
    void getPages() {
        CitizenPageableDto citizenPageableDto = CitizenPageableDto.builder().build();
        Pageable pageable = PageRequest.of(1, 1);

        when(personFacade.getCitizenPages(any(), any())).thenReturn(Page.empty());

        personController.getPersons(citizenPageableDto, pageable);

        verify(personFacade).getCitizenPages(citizenPageableDto, pageable);
    }

    @Test
    void addAddress() {
        AddressCreateDto dto = AddressCreateDto.builder().build();
        Long id = 1L;
        when(personFacade.addAddress(anyLong(), any())).thenReturn(AddressesGetDto.builder().build());

        personController.addAddress(id, dto);

        verify(personFacade).addAddress(id, dto);
    }

    @Test
    void getAddresses() {
        Long id = 1L;
        when(personFacade.getAddress(anyLong())).thenReturn(AddressesGetDto.builder().build());

        personController.getAddresses(id);

        verify(personFacade).getAddress(id);
    }

    @Test
    void unlinkAddress() {
        Long id = 1L;
        Long addrId = 1L;
        when(personFacade.unlinkAddress(anyLong(), anyLong())).thenReturn(AddressesGetDto.builder().build());

        personController.unlinkAddressFromCitizen(id, addrId);

        verify(personFacade).unlinkAddress(id, addrId);
    }

    @Test
    void putAddress() {
        AddressCreateDto dto = AddressCreateDto.builder().build();
        Long id = 1L;
        Long addrId = 1L;
        when(personFacade.changeAddress(anyLong(), anyLong(), any())).thenReturn(AddressesGetDto.builder().build());

        personController.changeAddressForCitizen(id, addrId, dto);

        verify(personFacade).changeAddress(id, addrId, dto);
    }
}