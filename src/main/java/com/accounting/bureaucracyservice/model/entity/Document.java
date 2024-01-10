package com.accounting.bureaucracyservice.model.entity;

import com.accounting.bureaucracyservice.configuration.AttributeEncryptor;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Document {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @Column(name = "number")
    @Convert(converter = AttributeEncryptor.class)
    private String number;
}
