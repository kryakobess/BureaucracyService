package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import com.accounting.bureaucracyservice.service.AddressService;
import com.accounting.bureaucracyservice.service.CitizenService;
import com.accounting.bureaucracyservice.service.validator.CitizenValidator;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import com.accounting.bureaucracyservice.service.repository.CitizenRepository;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenValidator citizenValidator;
    private final CitizenMapper citizenMapper;
    private final CitizenRepository citizenRepository;
    private final DocumentRepository documentRepository;
    private final AddressService addressService;

    @Override
    @Transactional
    public Citizen createCitizen(CitizenCreateDto citizenCreateDto) {
        log.info("Accounting new citizen");
        citizenValidator.validate(citizenCreateDto);
        Citizen citizen = citizenMapper.toModel(citizenCreateDto);

        checkCitizenToCreateAlreadyExists(citizen);
        var registrationAddress = addressService.getOrSave(citizenCreateDto.registrationAddress());
        citizen.setRegistrationAddress(registrationAddress);
        citizen.setAddresses(List.of(registrationAddress));
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

}
