package com.example.countries.service;

import com.example.countries.dto.CountryRequest;
import com.example.countries.dto.CountryResponse;
import com.example.countries.exception.CountryNotFoundException;
import com.example.countries.exception.InvalidSortException;
import com.example.countries.model.Country;
import com.example.countries.repository.CountryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CountryService {

    /** Propriedades pelas quais é permitido ordenar (evita ordenar por campos inexistentes). */
    private static final Set<String> SORTABLE = Set.of("id", "name", "capital", "region", "subRegion", "area");
    private static final Set<String> NUMERIC = Set.of("id", "area");

    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CountryResponse create(CountryRequest req) {
        Country country = new Country(req.name().trim(), req.capital().trim(),
                req.region().trim(), req.subRegion().trim(), req.area());
        return CountryResponse.from(repository.save(country));
    }

    public List<CountryResponse> list(String sortBy, String direction) {
        if (!SORTABLE.contains(sortBy)) {
            throw new InvalidSortException("sortBy inválido: '" + sortBy
                    + "'. Valores permitidos: id, name, capital, region, subRegion, area");
        }
        Sort.Direction dir;
        if ("asc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.ASC;
        } else if ("desc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.DESC;
        } else {
            throw new InvalidSortException("direction inválida: '" + direction + "'. Use 'asc' ou 'desc'.");
        }

        Sort.Order order = new Sort.Order(dir, sortBy);
        if (!NUMERIC.contains(sortBy)) {
            order = order.ignoreCase();
        }
        // "id" como critério de desempate para resultados estáveis
        Sort sort = Sort.by(order).and(Sort.by(Sort.Direction.ASC, "id"));

        return repository.findAll(sort).stream().map(CountryResponse::from).toList();
    }

    public CountryResponse get(Long id) {
        return CountryResponse.from(find(id));
    }

    @Transactional
    public CountryResponse update(Long id, CountryRequest req) {
        Country country = find(id);
        country.setName(req.name().trim());
        country.setCapital(req.capital().trim());
        country.setRegion(req.region().trim());
        country.setSubRegion(req.subRegion().trim());
        country.setArea(req.area());
        return CountryResponse.from(repository.save(country));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Country find(Long id) {
        return repository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));
    }
}
