package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class, AddressMapper.class})
public abstract class CitizenMapper {

    @Autowired
    protected AddressMapper addressMapper;

    public abstract CitizenDto toDto(Citizen entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documents", expression = "java(getIdentityDocuments(citizenCreateDto))")
    @Mapping(target = "addresses", expression = "java(java.util.List.of(addressMapper.toModel(citizenCreateDto.registrationAddress())))")
    public abstract Citizen toModel(CitizenCreateDto citizenCreateDto);

    protected List<Document> getIdentityDocuments(CitizenCreateDto citizenCreateDto) {
        return List.of(Document.builder()
                .documentType(DocumentType.IDENTITY_PASSPORT)
                .number(String.format("%d%d", citizenCreateDto.passportSeries().intValue(), citizenCreateDto.passportNumber().intValue()))
                .build());
    }
}
