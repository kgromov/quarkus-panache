package org.kgromov.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.kgromov.domain.Country;

@ApplicationScoped
public class CountryRepository implements PanacheRepositoryBase<Country, String> {
}
