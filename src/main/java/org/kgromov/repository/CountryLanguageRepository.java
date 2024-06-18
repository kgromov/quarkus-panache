package org.kgromov.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.kgromov.domain.CountryLanguage;
import org.kgromov.domain.LanguageCode;

import java.util.Optional;

@ApplicationScoped
public class CountryLanguageRepository implements PanacheRepositoryBase<CountryLanguage, LanguageCode> {

    public Optional<CountryLanguage> findByCountryCode(String code) {
//        return this.find("code", code).firstResultOptional();
        return this.find("languageCode.code", code).firstResultOptional();
    }
}
