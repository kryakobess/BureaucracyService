package com.accounting.bureaucracyservice.service.facade.impl;

import com.accounting.bureaucracyservice.model.dto.*;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.service.mapper.AddressMapper;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonFacadeImplTest {

    @Mock
    private CitizenService citizenService;

    @Mock
    private CitizenMapper citizenMapper;

    @Mock
    private AddressMapper addressMapper;

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

    @Test
    void getCitizenPages() {
        Page<Citizen> result = Page.empty();
        when(citizenService.getCitizensPage(any(), any())).thenReturn(result);
        personFacade.getCitizenPages(
                CitizenPageableDto.builder().build(),
                Pageable.unpaged()
        );

        verify(citizenService, atMostOnce()).getCitizensPage(any(), any());
    }

    @Test
    void addAddress() {
        Long id = 1L;
        AddressCreateDto addressCreateDto = AddressCreateDto.builder().build();
        Citizen citizen = new Citizen();
        when(citizenService.addAddressToCitizen(any(), any())).thenReturn(citizen);
        when(addressMapper.toAddressesGetDto(any(), any(), any())).thenReturn(AddressesGetDto.builder().build());

        personFacade.addAddress(id, addressCreateDto);

        verify(citizenService).addAddressToCitizen(addressCreateDto, id);
        verify(addressMapper).toAddressesGetDto(citizen.getId(), citizen.getRegistrationAddress(), citizen.getAddresses());
    }

    @Test
    void getAddress() {
        Long id = 1L;
        Citizen citizen = new Citizen();
        citizen.setId(id);
        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);
        when(addressMapper.toAddressesGetDto(anyLong(), any(), any())).thenReturn(AddressesGetDto.builder().build());

        personFacade.getAddress(id);

        verify(citizenService).getCitizenById(id);
        verify(addressMapper).toAddressesGetDto(citizen.getId(), citizen.getRegistrationAddress(), citizen.getAddresses());
    }

    @Test
    void unlinkAddress() {
        Long id = 1L;
        Long addressId = 1L;
        Citizen citizen = new Citizen();
        citizen.setId(id);
        when(citizenService.unlinkAddressFromCitizen(any(), any())).thenReturn(citizen);
        when(addressMapper.toAddressesGetDto(any(), any(), any())).thenReturn(AddressesGetDto.builder().build());

        personFacade.unlinkAddress(id, addressId);

        verify(citizenService).unlinkAddressFromCitizen(id, addressId);
        verify(addressMapper).toAddressesGetDto(citizen.getId(), citizen.getRegistrationAddress(), citizen.getAddresses());
    }

    @Test
    void changeAddress() {
        Long id = 1L;
        Long addressId = 1L;
        AddressCreateDto addressCreateDto = AddressCreateDto.builder().build();
        Citizen citizen = new Citizen();
        citizen.setId(id);
        when(citizenService.changeAddress(any(), any(), any())).thenReturn(citizen);
        when(addressMapper.toAddressesGetDto(any(), any(), any())).thenReturn(AddressesGetDto.builder().build());

        personFacade.changeAddress(id, addressId, addressCreateDto);

        verify(citizenService).changeAddress(id, addressId, addressCreateDto);
        verify(addressMapper).toAddressesGetDto(citizen.getId(), citizen.getRegistrationAddress(), citizen.getAddresses());
    }
}