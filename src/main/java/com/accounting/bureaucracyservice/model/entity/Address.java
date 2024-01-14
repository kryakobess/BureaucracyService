package com.accounting.bureaucracyservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(allocationSize = 1, name = "address_seq", sequenceName = "address_seq")
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment")
    private String apartment;
}
