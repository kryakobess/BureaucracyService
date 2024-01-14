package com.accounting.bureaucracyservice.service.service;

import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.querydsl.core.types.Predicate;

public interface PredicateCreationService {
    Predicate getCitizenQueryFilterPredicate(CitizenQueryFilter queryFilter);

}
