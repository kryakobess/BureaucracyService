package com.accounting.bureaucracyservice.service.repository;

import com.accounting.bureaucracyservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByRegionAndCityAndStreetAndHouseNumber(String region, String city, String street, String houseNumber);
}
