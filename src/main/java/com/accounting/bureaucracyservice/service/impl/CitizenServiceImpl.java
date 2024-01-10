package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.CitizenService;
import com.accounting.bureaucracyservice.service.CitizenValidator;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import com.accounting.bureaucracyservice.service.repository.CitizenRepository;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenValidator validator;
    private final CitizenMapper mapper;
    private final CitizenRepository repository;
    private final DocumentRepository documentRepository;

    @Override
    public CitizenDto createCitizen(CitizenCreateDto citizenCreateDto) {
        log.info("Accounting new citizen");
        validator.validate(citizenCreateDto);
        Citizen citizen = mapper.toModel(citizenCreateDto);
        checkCitizenToCreateAlreadyExists(citizen);
        return mapper.toDto(repository.save(citizen));
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

}
