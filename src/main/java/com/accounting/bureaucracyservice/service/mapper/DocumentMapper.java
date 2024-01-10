package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.DocumentDto;
import com.accounting.bureaucracyservice.model.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "citizen", ignore = true)
    Document toModel(DocumentDto dto);

    DocumentDto toDto(Document entity);
}
