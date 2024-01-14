package com.accounting.bureaucracyservice.service.service.impl;

import com.accounting.bureaucracyservice.model.entity.QAddress;
import com.accounting.bureaucracyservice.model.entity.QCitizen;
import com.accounting.bureaucracyservice.model.filters.AddressQueryFilter;
import com.accounting.bureaucracyservice.model.filters.CitizenQueryFilter;
import com.accounting.bureaucracyservice.service.service.PredicateCreationService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
public class PredicateCreationServiceImpl implements PredicateCreationService {

    @Override
    public Predicate getCitizenQueryFilterPredicate(CitizenQueryFilter queryFilter) {
        QCitizen citizen = QCitizen.citizen;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (isNotEmpty(queryFilter.getIds())) {
            booleanBuilder.and(citizen.id.in(queryFilter.getIds()));
        }

        if (isNotEmpty(queryFilter.getFirstNames())) {
            booleanBuilder.and(citizen.firstName.in(queryFilter.getFirstNames()));
        }

        if (isNotEmpty(queryFilter.getSecondNames())) {
            booleanBuilder.and(citizen.secondName.in(queryFilter.getSecondNames()));
        }

        if (nonNull(queryFilter.getRegistrationAddressQueryFilter())) {
            var regAddress = citizen.registrationAddress;
            booleanBuilder.and(getCustomEntityAddressPredicate(regAddress, queryFilter.getRegistrationAddressQueryFilter()));
        }

        return booleanBuilder.getValue();
    }

    private Predicate getCustomEntityAddressPredicate(QAddress address, AddressQueryFilter queryFilter) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (isNotEmpty(queryFilter.getIds())) {
            booleanBuilder.and(address.id.in(queryFilter.getIds()));
        }

        if (isNotEmpty(queryFilter.getRegions())) {
            booleanBuilder.and(address.region.in(queryFilter.getRegions()));
        }

        if (isNotEmpty(queryFilter.getCities())) {
            booleanBuilder.and(address.city.in(queryFilter.getCities()));
        }

        if (isNotEmpty(queryFilter.getStreets())) {
            booleanBuilder.and(address.street.in(queryFilter.getStreets()));
        }

        if (isNotEmpty(queryFilter.getIndexes())) {
            booleanBuilder.and(address.index.in(queryFilter.getIndexes()));
        }

        if (isNotEmpty(queryFilter.getHouseNumbers())) {
            booleanBuilder.and(address.houseNumber.in(queryFilter.getHouseNumbers()));
        }

        if (isNotEmpty(queryFilter.getApartments())) {
            booleanBuilder.and(address.apartment.in(queryFilter.getApartments()));
        }

        return booleanBuilder.getValue();
    }
}
