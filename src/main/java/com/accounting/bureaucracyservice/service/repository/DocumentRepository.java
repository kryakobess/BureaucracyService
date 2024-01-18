package com.accounting.bureaucracyservice.service.repository;

import com.accounting.bureaucracyservice.model.entity.Document;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    boolean existsByNumberAndCitizen_FirstNameAndCitizen_SecondNameAndDocumentType(String number, String firstName, String secondName, DocumentType documentType);

    boolean existsByNumberAndDocumentType(String number, DocumentType documentType);
}
