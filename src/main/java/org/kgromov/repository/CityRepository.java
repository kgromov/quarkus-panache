package org.kgromov.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.kgromov.domain.City;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City> {
}
