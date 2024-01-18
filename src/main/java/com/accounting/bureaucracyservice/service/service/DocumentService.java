package com.accounting.bureaucracyservice.service.service;

import com.accounting.bureaucracyservice.model.dto.DocumentCreateDto;
import com.accounting.bureaucracyservice.model.entity.Document;

public interface DocumentService {
    Document addDocumentToCitizen(Long citizenId, DocumentCreateDto createDto);

    Document changeDocument(Long citizenId, DocumentCreateDto createDto);
}
