package com.accounting.bureaucracyservice.service.repository;

import com.accounting.bureaucracyservice.model.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

}
