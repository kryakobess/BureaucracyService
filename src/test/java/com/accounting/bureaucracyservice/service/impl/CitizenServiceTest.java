package com.accounting.bureaucracyservice.service.impl;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.service.AddressService;
import com.accounting.bureaucracyservice.service.impl.CitizenServiceImpl;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import com.accounting.bureaucracyservice.service.repository.CitizenRepository;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import com.accounting.bureaucracyservice.service.validator.CitizenValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

    @Mock
    private CitizenValidator validator;
    @Mock
    private AddressService addressService;
    @Mock
    private CitizenMapper mapper;
    @Mock
    private CitizenRepository repository;
    @Mock
    private DocumentRepository documentRepository;
    @InjectMocks
    private CitizenServiceImpl citizenService;

    @Test
    public void successfullyCreateNewCitizen() {
        CitizenCreateDto createDto = CitizenCreateDto.builder().build();
        Citizen citizen = new Citizen();
        citizen.setDocuments(List.of(Document.builder().number("1234567890").build()));
        citizen.setFirstName("First");
        citizen.setSecondName("Second");
        when(mapper.toModel(any())).thenReturn(citizen);
        when(documentRepository.existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(any(), any(), any(), any()))
                .thenReturn(false);
        when(repository.save(any())).thenReturn(citizen);
        when(addressService.getOrSave(any())).thenReturn(new Address());

        citizenService.createCitizen(createDto);

        verify(mapper).toModel(createDto);
        verify(documentRepository).existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(
                citizen.getDocuments().get(0).getNumber(),
                citizen.getFirstName(),
                citizen.getSecondName(),
                DocumentType.IDENTITY_PASSPORT
        );
        verify(addressService).getOrSave(createDto.registrationAddress());
        verify(repository).save(citizen);
    }

    @Test
    public void throwExceptionWhileCreatingExistingCitizen() {
        CitizenCreateDto createDto = CitizenCreateDto.builder().build();
        Citizen citizen = new Citizen();
        citizen.setDocuments(List.of(Document.builder().number("1234567890").build()));
        citizen.setFirstName("First");
        citizen.setSecondName("Second");
        when(mapper.toModel(any())).thenReturn(citizen);
        when(documentRepository.existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(any(), any(), any(), any()))
                .thenReturn(true);

        assertThrows(BadRequestException.class, () -> citizenService.createCitizen(createDto));

        verify(mapper).toModel(createDto);
        verify(documentRepository).existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(
                citizen.getDocuments().get(0).getNumber(),
                citizen.getFirstName(),
                citizen.getSecondName(),
                DocumentType.IDENTITY_PASSPORT
        );
        verifyNoInteractions(repository);
    }

    @Test
    void isCitizenExistsByNameAndIdentityPassportNumber() {
        var firstName = "First";
        var secondName = "Second";
        var number = "1234567890";
        when(documentRepository.existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(any(), any(), any(), any())).thenReturn(true);
        var res = citizenService.isCitizenExistsByNameAndIdentityPassportNumber(firstName, secondName, number);

        verify(documentRepository).existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(number, firstName, secondName, DocumentType.IDENTITY_PASSPORT);
        assertTrue(res);
    }
}