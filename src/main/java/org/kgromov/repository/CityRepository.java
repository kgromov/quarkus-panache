package org.kgromov.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import org.kgromov.domain.City;
import org.kgromov.projection.CityByCountryProjection;
import org.kgromov.projection.LanguageUsage;
import org.kgromov.projection.CityProjection;

import java.util.List;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City> {

    private final EntityManager entityManager;

    public CityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CityProjection> findAllCityCountries() {
        // really confusing - why not query()?!
        return City.findAll(Sort.by("name"))
                .project(CityProjection.class)
                .list();
    }

    public CityProjection findCityCountryById(Long id) {
        return City.find("id", id)
                .project(CityProjection.class)
                .firstResult();
    }

    public List<CityByCountryProjection> findAllCitiesGroupedByCountry() {
        return City.find("""
                        select c.country.code, count(*) as count
                        from City c
                        group by c.country
                        order by count DESC
                        """)
                .project(CityByCountryProjection.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<LanguageUsage> findCityLanguagesNativeQuery(String city) {
        String query = """
                select cl.language, cl.percentage
                from City c
                join countrylanguage cl using(CountryCode)
                where c.name = :city
                order by cl.percentage DESC
                """;
        return entityManager.createNativeQuery(query)
                .setParameter("city", city)
                .getResultList();
    }

    public List<LanguageUsage> findCityLanguagesByName(String city) {
        return City.find("""
                        select c.country.code, cl.usage
                        from City c
                        join CountryLanguage cl on c.country.code = cl.languageCode.code
                        where c.name = :city
                        """,
//                        order by cl.usage DESC
                        Sort.descending("cl.usage"),
                        Parameters.with("city", city)
                )
                .project(LanguageUsage.class)
                .list();
    }

    @SuppressWarnings("unsafe")
    public String getCityMainLanguage(String city) {
        String query = """
                select cl.language
                from City c
                join countrylanguage cl using(CountryCode)
                where c.name = :city
                order by cl.percentage DESC
                limit 1
                """;
        return (String) entityManager.createNativeQuery(query)
                .setParameter("city", city)
                .getSingleResult();
    }
}
