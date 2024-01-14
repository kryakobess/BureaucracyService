package com.accounting.bureaucracyservice.model.filters;

import com.mysema.commons.lang.Pair;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CitizenQueryFilter {
    private List<Long> ids;

    private List<String> firstNames;

    private List<String> secondNames;

    private AddressQueryFilter registrationAddressQueryFilter;

    public boolean shouldFilter() {
        return isNotEmpty(ids)
                || isNotEmpty(firstNames)
                || isNotEmpty(secondNames)
                || (nonNull(registrationAddressQueryFilter) && registrationAddressQueryFilter.shouldFilter());
    }
}
