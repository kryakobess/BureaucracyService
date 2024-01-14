package com.accounting.bureaucracyservice.service.repository;

import com.accounting.bureaucracyservice.model.entity.Citizen;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long>, QuerydslPredicateExecutor<Citizen> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"registrationAddress"})
    Page<Citizen> findAll(Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"registrationAddress"})
    Page<Citizen> findAll(Predicate predicate, Pageable pageable);

}
