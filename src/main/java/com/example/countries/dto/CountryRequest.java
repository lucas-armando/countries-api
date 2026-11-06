package com.example.countries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CountryRequest(
        @NotBlank(message = "name é obrigatório") String name,
        @NotBlank(message = "capital é obrigatório") String capital,
        @NotBlank(message = "region é obrigatório") String region,
        @NotBlank(message = "subRegion é obrigatório") String subRegion,
        @NotNull(message = "area é obrigatório")
        @Positive(message = "area deve ser maior que zero") Double area
) {
}
