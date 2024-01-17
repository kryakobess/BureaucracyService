package com.accounting.bureaucracyservice.service.facade.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.filters.AddressQueryFilter;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<CitizenDto> getCitizenPages(
            List<Long> ids,
            List<String> firstNames,
            List<String> secondNames,
            List<String> regions,
            List<String> cities,
            List<String> streets,
            List<String> houseNumbers,
            List<String> apartments,
            Pageable pageable
    ) {
        log.info("""
                Get request getCitizenPages with values:
                ids: {},
                firstNames: {},
                secondNames: {},
                regions: {},
                cities: {},
                streets: {},
                houseNumbers: {},
                apartments: {}
                """, ids, firstNames, secondNames, regions, cities, streets, houseNumbers, apartments);

        var registrationAddressQueryFilter = AddressQueryFilter.builder()
                .regions(regions)
                .cities(cities)
                .streets(streets)
                .houseNumbers(houseNumbers)
                .apartments(apartments)
                .build();

        var citizenQueryFilter = CitizenQueryFilter.builder()
                .ids(ids)
                .firstNames(firstNames)
                .secondNames(secondNames)
                .registrationAddressQueryFilter(registrationAddressQueryFilter)
                .build();

        return citizenService.getCitizensPage(citizenQueryFilter, pageable)
                .map(citizenMapper::toDto);
    }
}
