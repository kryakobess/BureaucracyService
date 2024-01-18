package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.service.AddressService;
import com.accounting.bureaucracyservice.service.mapper.CitizenMapper;
import com.accounting.bureaucracyservice.service.repository.CitizenRepository;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import com.accounting.bureaucracyservice.service.service.PredicateCreationService;
import com.accounting.bureaucracyservice.service.service.impl.CitizenServiceImpl;
import com.accounting.bureaucracyservice.service.validator.CitizenValidator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Mock
    private PredicateCreationService predicateCreationService;
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

    @Test
    void getCitizenById_Successfully() {
        when(repository.findById(any())).thenReturn(Optional.of(new Citizen()));

        assertDoesNotThrow(() -> citizenService.getCitizenById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void getCitizenById_NotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> citizenService.getCitizenById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void getCitizenPage_shouldFilter() {
        CitizenQueryFilter queryFilter = CitizenQueryFilter.builder().ids(List.of(1L)).build();
        Pageable request = PageRequest.of(1, 1);
        Predicate predicate = new BooleanBuilder();

        when(predicateCreationService.getCitizenQueryFilterPredicate(any())).thenReturn(predicate);
        when(repository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(Page.empty());

        citizenService.getCitizensPage(queryFilter, request);

        verify(predicateCreationService).getCitizenQueryFilterPredicate(queryFilter);
        verify(repository).findAll(predicate, request);
    }

    @Test
    void getCitizenPage_shouldNotFilter() {
        CitizenQueryFilter queryFilter = new CitizenQueryFilter();
        Pageable request = PageRequest.of(1, 1);

        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        citizenService.getCitizensPage(queryFilter, request);

        verify(repository).findAll(request);
    }

    @Test
    void addAddress() {
        AddressCreateDto addressDto = AddressCreateDto.builder().build();
        Long id = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Citizen()));
        when(addressService.getOrSave(any())).thenReturn(new Address());

        citizenService.addAddressToCitizen(addressDto, id);

        verify(repository).findById(id);
        verify(addressService).getOrSave(addressDto);
    }

    @Test
    void unlinkAddressFromCitizen() {
        Long citizenId = 1L;
        Long addressId = 1L;
        Address regAddress = new Address();
        regAddress.setId(2L);
        Address address = new Address();
        address.setId(addressId);
        Citizen citizen = new Citizen();
        citizen.setRegistrationAddress(regAddress);
        citizen.getAddresses().addAll(List.of(regAddress, address));

        when(repository.findById(anyLong())).thenReturn(Optional.of(citizen));

        assertDoesNotThrow(() -> citizenService.unlinkAddressFromCitizen(citizenId, addressId));

        verify(repository).findById(citizenId);
    }

    @Test
    void unlinkAddressFromCitizen_RegAddressThrowsException() {
        Long citizenId = 1L;
        Long addressId = 1L;
        Address regAddress = new Address();
        regAddress.setId(addressId);
        Citizen citizen = new Citizen();
        citizen.setRegistrationAddress(regAddress);
        citizen.getAddresses().add(regAddress);

        when(repository.findById(anyLong())).thenReturn(Optional.of(citizen));

        assertThrows(BadRequestException.class, () -> citizenService.unlinkAddressFromCitizen(citizenId, addressId));

        verify(repository).findById(citizenId);
    }

    @Test
    void changeAddress() {
        Long citizenId = 1L;
        Long addressId = 1L;
        Address regAddress = new Address();
        regAddress.setId(addressId);
        Citizen citizen = new Citizen();
        citizen.setRegistrationAddress(regAddress);
        citizen.getAddresses().add(regAddress);
        var addressCreateDto = AddressCreateDto.builder().build();

        when(repository.findById(any())).thenReturn(Optional.of(citizen));
        when(addressService.getOrSave(any())).thenReturn(new Address());

        citizenService.changeAddress(citizenId, addressId, addressCreateDto);

        verify(repository).findById(citizenId);
        verify(addressService).getOrSave(addressCreateDto);
    }
}