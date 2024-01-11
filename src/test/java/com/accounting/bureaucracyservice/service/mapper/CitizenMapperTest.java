package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitizenMapperTest {

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private Citizen citizen;

    @Mock
    private CitizenCreateDto citizenCreateDto;

    @InjectMocks
    private CitizenMapperImpl citizenMapper;

    @Test
    public void shouldMapCitizenToDtoCorrectly() {
        // Arrange
        when(citizen.getId()).thenReturn(1L);
        when(citizen.getFirstName()).thenReturn("First");
        when(citizen.getSecondName()).thenReturn("Second");
        when(citizen.getDateOfBirth()).thenReturn(LocalDate.now());
        when(citizen.isApprovedAccount()).thenReturn(true);

        // Act
        CitizenDto dto = citizenMapper.toDto(citizen);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("First", dto.firstName());
        assertEquals("Second", dto.secondName());
        assertTrue(dto.approvedAccount());
    }

    @Test
    public void shouldMapCitizenCreateDtoToModelCorrectly() {
        // Arrange
        when(citizenCreateDto.firstName()).thenReturn("First");
        when(citizenCreateDto.secondName()).thenReturn("Second");
        when(citizenCreateDto.dateOfBirth()).thenReturn(LocalDate.now());
        when(citizenCreateDto.registrationAddress()).thenReturn(AddressCreateDto.builder().build());
        when(addressMapper.toModel(any())).thenReturn(new Address());

        // Act
        Citizen model = citizenMapper.toModel(citizenCreateDto);

        // Assert
        assertNotNull(model);
        assertEquals("First", model.getFirstName());
        assertEquals("Second", model.getSecondName());
        assertFalse(model.isApprovedAccount());
    }

    @Test
    public void shouldReturnNullWhenCitizenIsNull() {
        // Act
        CitizenDto dto = citizenMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    public void shouldReturnNullWhenCitizenCreateDtoIsNull() {
        // Act
        Citizen model = citizenMapper.toModel(null);

        // Assert
        assertNull(model);
    }
}