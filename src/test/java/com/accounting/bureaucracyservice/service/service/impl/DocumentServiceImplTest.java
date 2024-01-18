package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import com.accounting.bureaucracyservice.service.mapper.DocumentMapper;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.service.DocumentService;
import com.accounting.bureaucracyservice.service.validator.DocumentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private CitizenService citizenService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private DocumentValidator validator;
    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void addDocumentToCitizen() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        when(citizenService.getCitizenById(anyLong())).thenReturn(new Citizen());
        when(documentMapper.toModel(any())).thenReturn(new Document());
        when(documentRepository.existsByNumberAndDocumentType(any(), any())).thenReturn(false);

        documentService.addDocumentToCitizen(id, dto);

        verify(citizenService).getCitizenById(id);
        verify(documentMapper).toModel(dto);
        verify(documentRepository, atLeastOnce()).existsByNumberAndDocumentType(any(), any());
    }

    @Test
    void addDocumentToCitizen_DocumentAlreadyExists() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        when(citizenService.getCitizenById(anyLong())).thenReturn(new Citizen());
        when(documentMapper.toModel(any())).thenReturn(new Document());
        when(documentRepository.existsByNumberAndDocumentType(any(), any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> documentService.addDocumentToCitizen(id, dto));

        verify(citizenService).getCitizenById(id);
        verify(documentMapper).toModel(dto);
        verify(documentRepository, atLeastOnce()).existsByNumberAndDocumentType(any(), any());
    }

    @Test
    void addDocumentToCitizen_DocumentTypeAlreadyAssignedToCitizen() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        Document document = new Document();
        document.setDocumentType(DocumentType.INTERNATIONAL_PASSPORT);
        var citizen = new Citizen();
        citizen.setDocuments(List.of(document));
        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);
        when(documentMapper.toModel(any())).thenReturn(document);

        assertThrows(BadRequestException.class, () -> documentService.addDocumentToCitizen(id, dto));

        verify(citizenService).getCitizenById(id);
        verify(documentMapper).toModel(dto);
        verify(documentRepository, times(0)).existsByNumberAndDocumentType(any(), any());
    }

    @Test
    void changeDocument() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        Document document = new Document();
        document.setDocumentType(DocumentType.INTERNATIONAL_PASSPORT);
        var citizen = new Citizen();
        citizen.setDocuments(List.of(document));

        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);
        when(documentRepository.existsByNumberAndDocumentType(any(), any())).thenReturn(false);

        documentService.changeDocument(id, dto);

        verify(citizenService).getCitizenById(id);
        verify(documentRepository).existsByNumberAndDocumentType(dto.number(), DocumentType.INTERNATIONAL_PASSPORT);
    }

    @Test
    void changeDocument_NothingToChange() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        var citizen = new Citizen();

        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);

        assertThrows(NotFoundException.class, () -> documentService.changeDocument(id, dto));

        verify(citizenService).getCitizenById(id);
        verify(documentRepository, times(0)).existsByNumberAndDocumentType(dto.number(), DocumentType.INTERNATIONAL_PASSPORT);
    }

    @Test
    void changeDocument_DocumentAlreadyExists() {
        Long id = 1L;
        var dto = DocumentCreateDto.builder()
                .number("12345689")
                .documentType(DocumentType.INTERNATIONAL_PASSPORT.name())
                .build();
        Document document = new Document();
        document.setDocumentType(DocumentType.INTERNATIONAL_PASSPORT);
        var citizen = new Citizen();
        citizen.setDocuments(List.of(document));

        when(citizenService.getCitizenById(anyLong())).thenReturn(citizen);
        when(documentRepository.existsByNumberAndDocumentType(any(), any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> documentService.changeDocument(id, dto));

        verify(citizenService).getCitizenById(id);
        verify(documentRepository).existsByNumberAndDocumentType(dto.number(), DocumentType.INTERNATIONAL_PASSPORT);
    }
}