package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.service.AddressService;
import com.accounting.bureaucracyservice.service.mapper.AddressMapper;
import com.accounting.bureaucracyservice.service.repository.AddressRepository;
import com.accounting.bureaucracyservice.service.validator.AddressValidator;
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
        List<Address> accountedAddresses = addressRepository.findByRegionAndCityAndHouseNumber(
                addressDto.region(), addressDto.city(), addressDto.houseNumber()
        );
        var accountedAddress = accountedAddresses.stream()
                .filter(address -> address.getApartment().equals(addressDto.apartment()))
                .findFirst().orElse(null);

        if (accountedAddress != null) {
            log.info("Found existing address with id {}", accountedAddress.getId());
            return accountedAddress;
        } else {
            var addressToReturn = addressRepository.save(addressMapper.toModel(addressDto));
            log.info("Saving new address with id {}", addressToReturn.getId());
            return addressToReturn;
        }
    }

}
