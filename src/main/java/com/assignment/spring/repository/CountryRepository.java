package com.assignment.spring.repository;

import com.assignment.spring.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository  extends JpaRepository<Country, String> {
    Optional<Country> findByName(final String name);
}
