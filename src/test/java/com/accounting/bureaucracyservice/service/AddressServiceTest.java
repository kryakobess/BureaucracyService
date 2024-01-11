package com.accounting.bureaucracyservice.service;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.service.impl.AddressServiceImpl;
import com.accounting.bureaucracyservice.service.mapper.AddressMapper;
import com.accounting.bureaucracyservice.service.repository.AddressRepository;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    AddressValidator validator;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void getExistingAddress() {
        var dto = AddressCreateDto.builder()
                .region("Нижегородский")
                .city("Нижний Новгород")
                .street("Родионова")
                .houseNumber("136")
                .index(666222)
                .apartment("12А")
                .build();
        Address address = new Address();
        address.setId(1L);
        address.setApartment("12А");

        Mockito.when(addressRepository.findByRegionAndCityAndHouseNumber(any(), any(), any()))
                        .thenReturn(List.of(address));

        addressService.getOrSave(dto);

        verify(addressRepository).findByRegionAndCityAndHouseNumber(dto.region(), dto.city(), dto.houseNumber());
    }

    @Test
    void createNewAddress() {
        var dto = AddressCreateDto.builder()
                .region("Нижегородский")
                .city("Нижний Новгород")
                .street("Родионова")
                .houseNumber("136")
                .index(666222)
                .apartment("12А")
                .build();
        Address address = new Address();
        address.setId(1L);

        Mockito.when(addressRepository.findByRegionAndCityAndHouseNumber(any(), any(), any()))
                .thenReturn(List.of());
        Mockito.when(addressMapper.toModel(any())).thenReturn(address);
        Mockito.when(addressRepository.save(any())).thenReturn(address);

        addressService.getOrSave(dto);

        verify(addressRepository).findByRegionAndCityAndHouseNumber(dto.region(), dto.city(), dto.houseNumber());
        verify(addressMapper).toModel(dto);
        verify(addressRepository).save(address);
    }
}