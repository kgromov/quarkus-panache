package org.kgromov.web;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kgromov.domain.CountryLanguage;
import org.kgromov.repository.CountryLanguageRepository;

import java.util.List;

@ApplicationScoped
@Path("/languages")
@Produces(MediaType.APPLICATION_JSON)
public class CountryLanguageResource {
    @Inject
    CountryLanguageRepository languageRepository;

    @GET
    public List<CountryLanguage> getLanguages() {
        return languageRepository.listAll(Sort.by("languageCode.language"));
    }

    @GET
    @Path("{code}")
    public CountryLanguage getLanguageByCountryCode(String code) {
        return languageRepository.findByCountryCode(code).orElse(null);
    }
}

