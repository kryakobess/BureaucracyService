package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.ChangeCitizenDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.service.AddressService;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.service.PredicateCreationService;
import com.accounting.bureaucracyservice.service.validator.CitizenValidator;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import com.accounting.bureaucracyservice.service.repository.CitizenRepository;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenValidator citizenValidator;
    private final CitizenMapper citizenMapper;
    private final CitizenRepository citizenRepository;
    private final DocumentRepository documentRepository;
    private final AddressService addressService;
    private final PredicateCreationService predicateCreationService;

    @Override
    @Transactional
    public Citizen createCitizen(CitizenCreateDto citizenCreateDto) {
        log.info("Accounting new citizen");
        citizenValidator.validate(citizenCreateDto);
        Citizen citizen = citizenMapper.toModel(citizenCreateDto);

        checkCitizenToCreateAlreadyExists(citizen);
        var registrationAddress = addressService.getOrSave(citizenCreateDto.registrationAddress());
        citizen.setRegistrationAddress(registrationAddress);
        citizen.setAddresses(Set.of(registrationAddress));
        citizen.getDocuments().get(0).setCitizen(citizen);

        return citizenRepository.save(citizen);
    }

    private void checkCitizenToCreateAlreadyExists(Citizen citizen) {
        String identityPassportNumber = citizen.getDocuments().get(0).getNumber();
        var isExists = isCitizenExistsByNameAndIdentityPassportNumber(citizen.getFirstName(), citizen.getSecondName(), identityPassportNumber);
        if (isExists) {
            throw new BadRequestException("Citizen with this data already exists");
        }
    }

    @Override
    public boolean isCitizenExistsByNameAndIdentityPassportNumber(String firstName, String secondName, String number) {
        return documentRepository.existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(
                number,
                firstName,
                secondName,
                DocumentType.IDENTITY_PASSPORT
        );
    }

    @Override
    public Citizen getCitizenById(long id) {
        return citizenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Citizen with id=%d does not exist", id)));
    }

    @Override
    public Page<Citizen> getCitizensPage(CitizenQueryFilter queryFilter, Pageable pageable) {
        if (queryFilter.shouldFilter()) {
            return citizenRepository.findAll(predicateCreationService.getCitizenQueryFilterPredicate(queryFilter), pageable);
        }
        return citizenRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Citizen addAddressToCitizen(AddressCreateDto addressDto, Long citizenId) {
        var citizen = getCitizenById(citizenId);
        var address = addressService.getOrSave(addressDto);
        citizen.getAddresses().add(address);
        return citizen;
    }

    @Override
    @Transactional
    public Citizen unlinkAddressFromCitizen(Long citizenId, Long addressId) {
        var citizen = getCitizenById(citizenId);
        var registrationAddress = citizen.getRegistrationAddress();
        var addressToUnlink = findAddressForCitizenById(citizen, addressId);

        if (registrationAddress.getId().equals(addressToUnlink.getId())) {
            throw new BadRequestException("Cannot unlink registration address");
        }
        citizen.getAddresses().remove(addressToUnlink);
        return citizen;
    }

    @Override
    @Transactional
    public Citizen changeAddress(Long citizenId, Long addressId, AddressCreateDto addressCreateDto) {
        var citizen = getCitizenById(citizenId);
        var registrationAddress = citizen.getRegistrationAddress();
        var addressToChange = findAddressForCitizenById(citizen, addressId);
        var newAddress = addressService.getOrSave(addressCreateDto);

        if (addressToChange.getId().equals(registrationAddress.getId())) {
            citizen.setRegistrationAddress(newAddress);
        }
        citizen.getAddresses().remove(addressToChange);
        citizen.getAddresses().add(newAddress);
        return citizen;
    }

    @Override
    public Citizen changeCitizen(Long id, ChangeCitizenDto dto) {
        citizenValidator.validate(dto);
        var citizen = getCitizenById(id);

        citizen.setFirstName(dto.firstName());
        citizen.setSecondName(dto.secondName());
        citizen.setPhoneNumber(dto.phoneNumber());

        return citizenRepository.save(citizen);
    }

    private Address findAddressForCitizenById(Citizen citizen, Long addressId) {
        return citizen.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Address with id=%d does not linked to citizen with id=%d", addressId, citizen.getId())));
    }
}
