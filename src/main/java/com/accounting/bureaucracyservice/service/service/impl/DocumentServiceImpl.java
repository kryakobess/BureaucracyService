package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import com.accounting.bureaucracyservice.model.exceptions.BadRequestException;
import com.accounting.bureaucracyservice.model.exceptions.NotFoundException;
import com.accounting.bureaucracyservice.service.mapper.DocumentMapper;
import com.accounting.bureaucracyservice.service.repository.DocumentRepository;
import com.accounting.bureaucracyservice.service.service.CitizenService;
import com.accounting.bureaucracyservice.service.service.DocumentService;
import com.accounting.bureaucracyservice.service.validator.DocumentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final CitizenService citizenService;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentValidator validator;

    @Override
    public Document addDocumentToCitizen(Long citizenId, DocumentCreateDto createDto) {
        validator.validate(createDto);
        var citizen = citizenService.getCitizenById(citizenId);
        var document = documentMapper.toModel(createDto);

        boolean typeExists = citizen.getDocuments().stream().anyMatch(d -> d.getDocumentType().equals(document.getDocumentType()));
        if (typeExists) {
            throw new BadRequestException(String.format("Document with this type already exists for citizen with id=%d", citizenId));
        }
        checkExistsByNumberAndType(document.getNumber(), document.getDocumentType());

        document.setCitizen(citizen);
        return documentRepository.save(document);
    }

    @Override
    public Document changeDocument(Long citizenId, DocumentCreateDto createDto) {
        validator.validate(createDto);
        String number = createDto.number();
        DocumentType documentType = DocumentType.valueOf(createDto.documentType());

        var citizen = citizenService.getCitizenById(citizenId);
        var documentToChange = citizen.getDocuments().stream()
                .filter(document -> document.getDocumentType().equals(documentType))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Citizen id=%d does not contain document with type=%s", citizenId, documentType)));
        checkExistsByNumberAndType(number, documentType);

        documentToChange.setNumber(number);
        return documentRepository.save(documentToChange);
    }

    private void checkExistsByNumberAndType(String number, DocumentType documentType) {
        if (documentRepository.existsByNumberAndDocumentType(number, documentType)) {
            throw new BadRequestException("Document with this number already exists");
        }
    }
}
