package com.accounting.bureaucracyservice.service.facade.impl;

import com.accounting.bureaucracyservice.model.dto.*;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.filters.AddressQueryFilter;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.mapper.AddressMapper;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonFacadeImpl implements PersonFacade {

    private final CitizenService citizenService;

    private final CitizenMapper citizenMapper;

    private final AddressMapper addressMapper;

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

    @Override
    public Page<CitizenDto> getCitizenPages(
            CitizenPageableDto citizenPageableDto,
            Pageable pageable
    ) {
        log.info("Get request getCitizenPages with values: {}", citizenPageableDto);

        var registrationAddressQueryFilter = AddressQueryFilter.builder()
                .regions(citizenPageableDto.regions())
                .cities(citizenPageableDto.cities())
                .streets(citizenPageableDto.streets())
                .houseNumbers(citizenPageableDto.houseNumbers())
                .apartments(citizenPageableDto.apartments())
                .build();

        var citizenQueryFilter = CitizenQueryFilter.builder()
                .ids(citizenPageableDto.ids())
                .firstNames(citizenPageableDto.firstNames())
                .secondNames(citizenPageableDto.secondNames())
                .registrationAddressQueryFilter(registrationAddressQueryFilter)
                .build();

        return citizenService.getCitizensPage(citizenQueryFilter, pageable)
                .map(citizenMapper::toDto);
    }

    @Override
    public AddressesGetDto addAddress(Long id, AddressCreateDto addressCreateDto) {
        log.info("Post request addAddress for citizen with id={}, address:{}", id, addressCreateDto);
        return mapCitizenToAddressGetDto(citizenService.addAddressToCitizen(addressCreateDto, id));
    }

    @Override
    public AddressesGetDto getAddress(Long id) {
        log.info("Get request getAddress for citizen with id={}", id);
        var citizen = citizenService.getCitizenById(id);
        return mapCitizenToAddressGetDto(citizen);
    }

    @Override
    public AddressesGetDto unlinkAddress(Long id, Long addressId) {
        log.info("Delete request unlinkAddress with addressId={} for citizen with id={}", addressId, id);
        var citizen = citizenService.unlinkAddressFromCitizen(id, addressId);
        return mapCitizenToAddressGetDto(citizen);
    }

    @Override
    public AddressesGetDto changeAddress(Long id, Long addressId, AddressCreateDto addressCreateDto) {
        log.info("Put request changeAddress with addressId={} for citizen with id={}", addressId, id);
        var citizen = citizenService.changeAddress(id, addressId, addressCreateDto);
        return mapCitizenToAddressGetDto(citizen);
    }

    private AddressesGetDto mapCitizenToAddressGetDto(Citizen citizen) {
        return addressMapper.toAddressesGetDto(citizen.getId(), citizen.getRegistrationAddress(), citizen.getAddresses());
    }
}
