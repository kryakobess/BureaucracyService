package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    @Mock
    private Address address;

    private final AddressMapper addressMapper = new AddressMapperImpl();

    @Test
    public void dtoToEntity() {
        var dto = AddressCreateDto.builder()
                .region("Нижегородский")
                .city("Нижний Новгород")
                .street("Родионова")
                .houseNumber("136")
                .index(666222)
                .apartment("12А")
                .build();

        var address = addressMapper.toModel(dto);

        assertNull(address.getId());
        assertEquals(dto.region(), address.getRegion());
        assertEquals(dto.city(), address.getCity());
        assertEquals(dto.street(), address.getStreet());
        assertEquals(dto.houseNumber(), address.getHouseNumber());
        assertEquals(dto.index(), address.getIndex());
        assertEquals(dto.apartment(), address.getApartment());
    }

    @Test
    public void shouldMapAddressToDtoCorrectly() {
        when(address.getId()).thenReturn(1L);
        when(address.getIndex()).thenReturn(123456);
        when(address.getRegion()).thenReturn("Регион");
        when(address.getCity()).thenReturn("Город");
        when(address.getStreet()).thenReturn("Улица");
        when(address.getHouseNumber()).thenReturn("123");
        when(address.getApartment()).thenReturn("456");

        AddressDto dto = addressMapper.toDto(address);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(123456, dto.index());
        assertEquals("Регион", dto.region());
        assertEquals("Город", dto.city());
        assertEquals("Улица", dto.street());
        assertEquals("123", dto.houseNumber());
        assertEquals("456", dto.apartment());
    }

    @Test
    public void shouldReturnNullWhenAddressIsNull() {
        AddressDto dto = addressMapper.toDto(null);

        assertNull(dto);
    }

    @Test
    public void toDtos() {
        List<Address> addresses = List.of(new Address());

        var res = addressMapper.toDtos(addresses);

        assertEquals(1, res.size());
    }

    @Test
    public void toDtos_IsNull() {
        List<Address> addresses = null;

        var res = addressMapper.toDtos(addresses);

        assertNull(res);
    }

    @Test
    public void toAddressesGetDto() {
        Long id = 1L;
        Address address = new Address();
        address.setId(1L);
        List<Address> addresses = List.of(address);

        var res = addressMapper.toAddressesGetDto(id, address, addresses);

        assertEquals(id, res.citizenId());
        assertEquals(address.getId(), res.registrationAddress().id());
        assertEquals(1, res.addresses().size());
    }

    @Test
    public void toAddressesGetDto_IsNull() {
        Long id = null;
        Address address = null;
        List<Address> addresses = null;

        var res = addressMapper.toAddressesGetDto(id, address, addresses);

        assertNull(res);
    }
}