package com.example.countries.controller;

import com.example.countries.dto.CountryRequest;
import com.example.countries.dto.CountryResponse;
import com.example.countries.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    /** Criar país. */
    @PostMapping
    public ResponseEntity<CountryResponse> create(@Valid @RequestBody CountryRequest request) {
        CountryResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    /** Listar países, com ordenação opcional: ?sortBy=name&direction=desc */
    @GetMapping
    public List<CountryResponse> list(@RequestParam(defaultValue = "id") String sortBy,
                                      @RequestParam(defaultValue = "asc") String direction) {
        return service.list(sortBy, direction);
    }

    /** Obter um país pelo identificador. */
    @GetMapping("/{id}")
    public CountryResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    /** Modificar um país existente. */
    @PutMapping("/{id}")
    public CountryResponse update(@PathVariable Long id, @Valid @RequestBody CountryRequest request) {
        return service.update(id, request);
    }

    /** Eliminar um país. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
