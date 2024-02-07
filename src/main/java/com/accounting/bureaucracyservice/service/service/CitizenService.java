package com.accounting.bureaucracyservice.service.service;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.ChangeCitizenDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CitizenService {
    Citizen createCitizen(CitizenCreateDto citizenCreateDto);
    boolean isCitizenExistsByNameAndDocument(String firstName, String secondName, DocumentType documentType, String number);
    Citizen getCitizenById(long id);
    Page<Citizen> getCitizensPage(CitizenQueryFilter queryFilter, Pageable pageable);
    Citizen addAddressToCitizen(AddressCreateDto addressDto, Long citizenId);
    Citizen unlinkAddressFromCitizen(Long citizenId, Long addressId);
    Citizen changeAddress(Long citizenId, Long addressId, AddressCreateDto addressCreateDto);
    Citizen changeCitizen(Long id, ChangeCitizenDto dto);
    Citizen getCitizenByNameAndDocument(String firstName, String secondName, DocumentType documentType, String number);
}
