package com.accounting.bureaucracyservice.model.filters;

import lombok.*;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AddressQueryFilter {
    private List<Long> ids;

    private List<Integer> indexes;

    private List<String> regions;

    private List<String> cities;

    private List<String> streets;

    private List<String> houseNumbers;

    private List<String> apartments;

    public boolean shouldFilter() {
        return isNotEmpty(ids)
                || isNotEmpty(indexes)
                || isNotEmpty(regions)
                || isNotEmpty(cities)
                || isNotEmpty(streets)
                || isNotEmpty(houseNumbers)
                || isNotEmpty(apartments);
    }
}
