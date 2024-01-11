package com.accounting.bureaucracyservice.service.repository;

import com.accounting.bureaucracyservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByRegionAndCityAndHouseNumber(String region, String city, String houseNumber);
}
