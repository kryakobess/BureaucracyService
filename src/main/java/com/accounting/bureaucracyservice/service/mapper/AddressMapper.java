package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.AddressCreateDto;
import com.accounting.bureaucracyservice.model.dto.AddressDto;
import com.accounting.bureaucracyservice.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toModel(AddressCreateDto addressDto);

    List<AddressDto> toDtos(List<Address> entities);

    AddressDto toDto(Address address);
}
