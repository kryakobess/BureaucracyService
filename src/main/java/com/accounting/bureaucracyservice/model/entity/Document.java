package com.accounting.bureaucracyservice.model.entity;

import com.accounting.bureaucracyservice.configuration.AttributeEncryptor;
import com.accounting.bureaucracyservice.model.enums.DocumentType;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq")
    @SequenceGenerator(allocationSize = 1, name = "document_seq", sequenceName = "document_seq")
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
