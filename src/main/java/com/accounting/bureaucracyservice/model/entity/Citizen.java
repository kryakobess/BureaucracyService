package com.accounting.bureaucracyservice.model.entity;

import com.accounting.bureaucracyservice.configuration.AttributeEncryptor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Citizen {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @Convert(converter = AttributeEncryptor.class)
    private String firstName;

    @Column(name = "second_name")
    @Convert(converter = AttributeEncryptor.class)
    private String secondName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @OneToMany(mappedBy = "citizen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documents;

    @ManyToMany
    @JoinTable(
            name = "REGISTRATION_ADDRESS",
            joinColumns = {@JoinColumn(name = "CITIZEN_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ADDRESS_ID")}
    )
    private List<Address> addresses;

    @Column(name = "IS_APPROVED")
    private boolean approvedAccount = false;
}
