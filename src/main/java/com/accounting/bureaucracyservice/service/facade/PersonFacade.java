package com.accounting.bureaucracyservice.service.facade;

import com.accounting.bureaucracyservice.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonFacade {
    CitizenDto createPerson(CitizenCreateDto createDto);

    CitizenDto getPersonById(long id);

    Page<CitizenDto> getCitizenPages(
            CitizenPageableDto citizenPageableDto,
            Pageable pageable
    );

    AddressesGetDto addAddress(Long id, AddressCreateDto addressCreateDto);

    AddressesGetDto getAddress(Long id);

    AddressesGetDto unlinkAddress(Long id, Long addressId);

    AddressesGetDto changeAddress(Long id, Long addressId, AddressCreateDto addressCreateDto);
}
