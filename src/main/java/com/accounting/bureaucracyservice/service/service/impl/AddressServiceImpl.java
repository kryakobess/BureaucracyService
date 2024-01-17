package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.service.service.AddressService;
import com.accounting.bureaucracyservice.service.mapper.AddressMapper;
import com.accounting.bureaucracyservice.service.repository.AddressRepository;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressValidator validator;
    private final AddressMapper addressMapper;

    @Override
    public Address getOrSave(AddressCreateDto addressDto) {
        log.info("Checking for address: {}", addressDto);
        validator.validate(addressDto);

        var accountedAddress = findAddressByRegionCityHouseAndApartmentIfExists(
                addressDto.region(),
                addressDto.city(),
                addressDto.street(),
                addressDto.houseNumber(),
                addressDto.apartment()
        );

        if (accountedAddress != null) {
            log.info("Found existing address with id {}", accountedAddress.getId());
            return accountedAddress;
        } else {
            var addressToReturn = addressRepository.save(addressMapper.toModel(addressDto));
            log.info("Saving new address with id {}", addressToReturn.getId());
            return addressToReturn;
        }
    }

    private Address findAddressByRegionCityHouseAndApartmentIfExists(
            String region,
            String city,
            String street,
            String houseNumber,
            @Nullable String apartment
    ) {
        List<Address> accountedAddresses = addressRepository.findByRegionAndCityAndStreetAndHouseNumber(region, city, street, houseNumber);
         return accountedAddresses.stream()
                .filter(address -> address.getApartment().equals(apartment))
                .findFirst().orElse(null);
    }

}
