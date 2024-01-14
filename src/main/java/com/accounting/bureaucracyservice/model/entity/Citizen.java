package com.accounting.bureaucracyservice.model.entity;

import com.accounting.bureaucracyservice.configuration.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citizen_seq")
    @SequenceGenerator(allocationSize = 1, name = "citizen_seq", sequenceName = "citizen_seq")
    private Long id;

    @Column(name = "first_name")
    @Convert(converter = AttributeEncryptor.class)
    private String firstName;

    @Column(name = "second_name")
    @Convert(converter = AttributeEncryptor.class)
    private String secondName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    @Convert(converter = AttributeEncryptor.class)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "registration_address_id")
    private Address registrationAddress;

    @OneToMany(mappedBy = "citizen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documents;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CITIZEN_ADDRESS",
            joinColumns = {@JoinColumn(name = "CITIZEN_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ADDRESS_ID")}
    )
    private List<Address> addresses;

    @Column(name = "IS_APPROVED")
    private boolean approvedAccount = false;
}
