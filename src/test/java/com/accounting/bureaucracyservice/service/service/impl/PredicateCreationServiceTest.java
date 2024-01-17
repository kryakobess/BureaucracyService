package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.filters.AddressQueryFilter;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.service.PredicateCreationService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredicateCreationServiceTest {

    private PredicateCreationServiceImpl predicateCreationService = new PredicateCreationServiceImpl();

    @Test
    void getCitizenQueryFilterPredicate() {
        var addressQueryFilter = AddressQueryFilter.builder()
                .ids(List.of(1L))
                .streets(List.of("q"))
                .apartments(List.of("1"))
                .houseNumbers(List.of("1"))
                .cities(List.of("N"))
                .regions(List.of("n"))
                .indexes(List.of(1))
                .build();
        var queryFilter = CitizenQueryFilter.builder()
                .ids(List.of(1L))
                .firstNames(List.of("first"))
                .secondNames(List.of("second"))
                .registrationAddressQueryFilter(addressQueryFilter)
                .build();

        var res = predicateCreationService.getCitizenQueryFilterPredicate(queryFilter);

        assertNotNull(res);
    }
}