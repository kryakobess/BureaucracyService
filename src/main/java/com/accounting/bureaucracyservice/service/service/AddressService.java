package com.accounting.bureaucracyservice.service.service;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.entity.Address;

public interface AddressService {
    Address getOrSave(AddressCreateDto addressDto);
}
