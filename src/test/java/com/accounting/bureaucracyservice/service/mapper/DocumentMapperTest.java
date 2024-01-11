package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.DocumentDto;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentMapperTest {

    @Mock
    private DocumentDto documentDto;

    @Mock
    private Document document;

    private final DocumentMapper documentMapper = new DocumentMapperImpl();


    @Test
    public void shouldMapDocumentDtoToModelCorrectly() {
        // Arrange
        when(documentDto.documentType()).thenReturn(DocumentType.DRIVER_LICENCE);
        when(documentDto.number()).thenReturn("1234567890");

        // Act
        Document model = documentMapper.toModel(documentDto);

        // Assert
        assertNotNull(model);
        assertEquals(DocumentType.DRIVER_LICENCE, model.getDocumentType());
        assertEquals("1234567890", model.getNumber());
    }

    @Test
    public void shouldMapDocumentToDtoCorrectly() {
        // Arrange
        when(document.getId()).thenReturn(1L);
        when(document.getDocumentType()).thenReturn(DocumentType.INTERNATIONAL_PASSPORT);
        when(document.getNumber()).thenReturn("1234567890");

        // Act
        DocumentDto dto = documentMapper.toDto(document);

        // Assert
        assertNotNull(dto);
        assertEquals(DocumentType.INTERNATIONAL_PASSPORT, dto.documentType());
        assertEquals("1234567890", dto.number());
    }

    @Test
    public void shouldReturnNullWhenDocumentDtoIsNull() {
        // Act
        Document model = documentMapper.toModel(null);

        // Assert
        assertNull(model);
    }

    @Test
    public void shouldReturnNullWhenDocumentIsNull() {
        // Act
        DocumentDto dto = documentMapper.toDto(null);

        // Assert
        assertNull(dto);
    }
}