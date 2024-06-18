package org.kgromov.web;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kgromov.domain.City;
import org.kgromov.projection.CityByCountryProjection;
import org.kgromov.projection.CityProjection;
import org.kgromov.repository.CityRepository;

import java.util.List;

@ApplicationScoped
@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {
    @Inject
    CityRepository cityRepository;

    @GET
    public List<City> getCities() {
        List<City> cities = City.listAll();
        return cityRepository.listAll(Sort.by("name"));
    }

    @GET
    @Path("{id}")
    public City getCity(Long id) {
        return City.findById(id);
    }

    @GET
    @Path("/projection")
    public List<CityProjection> getCitiesProjection() {
        // really confusing - why not query()?!
        PanacheQuery<PanacheEntityBase> query = City.findAll(Sort.by("name"));
        return query.project(CityProjection.class).list();
    }

    @GET
    @Path("{id}/projection")
    public CityProjection getCityProjection(Long id) {
        return City.find("id", id)
                .project(CityProjection.class)
                .firstResult();
    }

    @GET
    @Path("/byCountry")
    public List<CityByCountryProjection> getCitiesByCountry() {
        PanacheQuery<PanacheEntityBase> query = City.find("""
                select c.country.code, count(*) as count from City c 
                group by c.country 
                order by count DESC
                """);
    /*    PanacheQuery<PanacheEntityBase> query = City.find("""
                select ct.code, count(*) as count 
                from City c 
                join c.country ct
                group by ct.code 
                order by count DESC
                """);*/
        return query.project(CityByCountryProjection.class).list();
    }
}

