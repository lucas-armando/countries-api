package com.example.countries.dto;

import com.example.countries.model.Country;

public record CountryResponse(
        Long id,
        String name,
        String capital,
        String region,
        String subRegion,
        Double area
) {
    public static CountryResponse from(Country c) {
        return new CountryResponse(c.getId(), c.getName(), c.getCapital(),
                c.getRegion(), c.getSubRegion(), c.getArea());
    }
}
