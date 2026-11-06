package com.example.countries.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long id) {
        super("País com id " + id + " não encontrado");
    }
}
