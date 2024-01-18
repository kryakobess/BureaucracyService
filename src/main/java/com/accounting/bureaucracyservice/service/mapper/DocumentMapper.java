package com.accounting.bureaucracyservice.service.mapper;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.dto.DocumentDto;
import com.accounting.bureaucracyservice.model.dto.DocumentsGetDto;
import com.accounting.bureaucracyservice.model.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "citizen", ignore = true)
    @Mapping(target = "id", ignore = true)
    Document toModel(DocumentCreateDto dto);

    @Mapping(target = "citizenId", source = "entity.citizen.id")
    DocumentDto toDto(Document entity);

    List<DocumentDto> toDtos(List<Document> entities);

    @Mapping(target = "citizenId", source = "id")
    @Mapping(target = "documents", source = "documents")
    DocumentsGetDto toDocumentsGetDto(Long id, List<Document> documents);
}
