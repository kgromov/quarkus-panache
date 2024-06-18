package org.kgromov.web;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kgromov.domain.Country;
import org.kgromov.repository.CountryRepository;

import java.util.List;

@ApplicationScoped
@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
public class CountryResource {
    @Inject
    CountryRepository countryRepository;

    @GET
    public List<Country> getCountries() {
//        return CountryEntity.listAll(Sort.by("name"));
        return countryRepository.listAll(Sort.by("name"));
    }

    @GET
    @Path("{code}")
    public Country getCountry(String code) {
//        return CountryEntity.findById(id);
        return countryRepository.findById(code);
    }
}

